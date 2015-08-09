package com.wakacommerce.common.web.payment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayCheckoutService;
import com.wakacommerce.common.payment.service.PaymentGatewayConfiguration;
import com.wakacommerce.common.payment.service.PaymentGatewayWebResponsePrintService;
import com.wakacommerce.common.payment.service.PaymentGatewayWebResponseService;
import com.wakacommerce.common.vendor.service.exception.PaymentException;
import com.wakacommerce.common.web.controller.WakaAbstractController;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public abstract class PaymentGatewayAbstractController extends WakaAbstractController {

    protected static final Log LOG = LogFactory.getLog(PaymentGatewayAbstractController.class);
    public static final String PAYMENT_PROCESSING_ERROR = "PAYMENT_PROCESSING_ERROR";

    protected static String baseRedirect = "redirect:/";
    protected static String baseErrorView = "/error";
    protected static String baseOrderReviewRedirect = "redirect:/checkout";
    protected static String baseConfirmationRedirect = "redirect:/confirmation";
    protected static String baseCartRedirect = "redirect:/cart";

    //Externalized Generic Payment Error Message
    protected static String processingErrorMessage = "cart.paymentProcessingError";

    @Autowired(required=false)
    @Qualifier("blPaymentGatewayCheckoutService")
    protected PaymentGatewayCheckoutService paymentGatewayCheckoutService;

    @Resource(name = "blPaymentGatewayWebResponsePrintService")
    protected PaymentGatewayWebResponsePrintService webResponsePrintService;

    public Long applyPaymentToOrder(PaymentResponseDTO responseDTO) throws IllegalArgumentException {
        if (LOG.isErrorEnabled()) {
            if (paymentGatewayCheckoutService == null) {
                LOG.trace("applyPaymentToOrder: PaymentCheckoutService is null. Please check your configuration.");
            }
        }

        if (paymentGatewayCheckoutService != null) {
            return paymentGatewayCheckoutService.applyPaymentToOrder(responseDTO, getConfiguration());
        }
        return null;
    }

    public String initiateCheckout(Long orderId) throws Exception {
        String orderNumber = null;
        if (LOG.isErrorEnabled()) {
            if (paymentGatewayCheckoutService == null) {
                LOG.trace("initiateCheckout: PaymentCheckoutService is null. Please check your configuration.");
            }
        }

        if (paymentGatewayCheckoutService != null && orderId != null) {
            orderNumber = paymentGatewayCheckoutService.initiateCheckout(orderId);
        }

        if (orderNumber == null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("The result from calling initiateCheckout with paymentCheckoutService and orderId: " + orderId + " is null");
            }
        }

        return orderNumber;
    }

    public String lookupOrderNumberFromOrderId(PaymentResponseDTO responseDTO) {
        String orderNumber = null;
        if (LOG.isErrorEnabled()) {
            if (paymentGatewayCheckoutService == null) {
                LOG.trace("lookupOrderNumberFromOrderId: PaymentCheckoutService is null. Please check your configuration.");
            }
        }

        if (paymentGatewayCheckoutService != null) {
            orderNumber = paymentGatewayCheckoutService.lookupOrderNumberFromOrderId(responseDTO);
        }

        if (orderNumber == null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("The result from calling lookupOrderNumberFromOrderId is null");
            }
        }

        return orderNumber;
    }

    // ***********************************************
    // Common Result Processing
    // ***********************************************

    public String process(Model model, HttpServletRequest request,
                          final RedirectAttributes redirectAttributes) throws PaymentException {
        Long orderPaymentId = null;

        try {
            PaymentResponseDTO responseDTO = getWebResponseService().translateWebResponse(request);
            if (LOG.isTraceEnabled()) {
                LOG.trace("HTTPRequest translated to Raw Response: " +  responseDTO.getRawResponse());
            }

            orderPaymentId = applyPaymentToOrder(responseDTO);

            if (!responseDTO.isSuccessful()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("The Response DTO is marked as unsuccessful. Delegating to the " +
                            "payment module to handle an unsuccessful transaction");
                }

                handleUnsuccessfulTransaction(model, redirectAttributes, responseDTO);
                return getErrorViewRedirect();
            }

            if (!responseDTO.isValid()) {
                throw new PaymentException("The validity of the response cannot be confirmed." +
                        "Check the Tamper Proof Seal for more details.");
            }

            String orderId = responseDTO.getOrderId();
            if (orderId == null) {
                throw new RuntimeException("Order ID must be set on the Payment Response DTO");
            }

            if (responseDTO.isCompleteCheckoutOnCallback()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("The Response DTO for this Gateway is configured to complete checkout on callback. " +
                            "Initiating Checkout with Order ID: " + orderId);
                }

                String orderNumber = initiateCheckout(Long.parseLong(orderId));
                return getConfirmationViewRedirect(orderNumber);
            } else {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("The Gateway is configured to not complete checkout. " +
                            "Redirecting to the Order Review Page for Order ID: " + orderId);
                }

                return getOrderReviewRedirect();
            }

        } catch (Exception e) {

            if (LOG.isErrorEnabled()) {
                LOG.error("HTTPRequest - " + webResponsePrintService.printRequest(request));

                LOG.error("An exception was caught either from processing the response and applying the payment to " +
                        "the order, or an activity in the checkout workflow threw an exception. Attempting to " +
                        "mark the payment as invalid and delegating to the payment module to handle any other " +
                        "exception processing. The error caught was: " + e);
            }
            
            if (paymentGatewayCheckoutService != null && orderPaymentId != null) {
                paymentGatewayCheckoutService.markPaymentAsInvalid(orderPaymentId);
            }

            handleProcessingException(e, redirectAttributes);
        }

        return getErrorViewRedirect();
    }

    public abstract void handleProcessingException(Exception e, final RedirectAttributes redirectAttributes)
            throws PaymentException;

    public abstract void handleUnsuccessfulTransaction(Model model, final RedirectAttributes redirectAttributes,
                                                       PaymentResponseDTO responseDTO) throws PaymentException;

    public abstract String getGatewayContextKey();

    public abstract PaymentGatewayWebResponseService getWebResponseService();

    public abstract PaymentGatewayConfiguration getConfiguration();

    public abstract String returnEndpoint(Model model, HttpServletRequest request,
                                          final RedirectAttributes redirectAttributes,
                                          Map<String, String> pathVars) throws PaymentException;

    public abstract String errorEndpoint(Model model, HttpServletRequest request,
                                         final RedirectAttributes redirectAttributes,
                                         Map<String, String> pathVars) throws PaymentException;


    protected String getErrorViewRedirect() {
        //delegate to the modules endpoint as there may be additional processing that is involved
        return baseRedirect + getGatewayContextKey() + baseErrorView;
    }

    protected String getCartViewRedirect() {
        return baseCartRedirect;
    }

    public String getOrderReviewRedirect()  {
        return baseOrderReviewRedirect;
    }

    public String getBaseConfirmationRedirect() {
        return baseConfirmationRedirect;
    }

    protected String getConfirmationViewRedirect(String orderNumber) {
        return getBaseConfirmationRedirect() + "/" + orderNumber;
    }

    public static String getProcessingErrorMessage() {
        return processingErrorMessage;
    }
}
