  
package com.wakacommerce.core.checkout.service.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayRollbackService;
import com.wakacommerce.common.vendor.service.exception.PaymentException;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.payment.service.OrderPaymentService;

import javax.annotation.Resource;

/**
 * This default implementation will create a compensating transaction response based on the transaction type passed in
 * for any Passthrough Order Payments on the order.
 * This is by default initiated from BroadleafCheckoutController.processPassthroughCheckout();
 * If an error occurs in the checkout workflow, the {@link ConfirmPaymentsRollbackHandler} gets invoked and will call this class.
 *
 *  
 */
@Service("blPassthroughPaymentRollbackService")
public class PassthroughPaymentRollbackServiceImpl implements PaymentGatewayRollbackService {

    protected static final Log LOG = LogFactory.getLog(PassthroughPaymentRollbackServiceImpl.class);

    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Resource(name = "blOrderPaymentService")
    protected OrderPaymentService orderPaymentService;

    @Override
    public PaymentResponseDTO rollbackAuthorize(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {

        if (LOG.isTraceEnabled()) {
            LOG.trace("Passthrough Payment Gateway - Rolling back authorize transaction with amount: " + transactionToBeRolledBack.getTransactionTotal());
        }

        if (transactionToBeRolledBack.getAdditionalFields().containsKey(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)) {

            return new PaymentResponseDTO(
                    PaymentType.getInstance((String)transactionToBeRolledBack.getAdditionalFields().get(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)),
                    PaymentGatewayType.PASSTHROUGH)
                    .rawResponse("rollback authorize - successful")
                    .successful(true)
                    .paymentTransactionType(PaymentTransactionType.REVERSE_AUTH)
                    .amount(new Money(transactionToBeRolledBack.getTransactionTotal()));

        }

        throw new PaymentException("Make sure transaction contains a Passthrough Payment Type");
    }

    @Override
    public PaymentResponseDTO rollbackCapture(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {
        throw new PaymentException("The Rollback Capture method is not supported for this module");
    }

    @Override
    public PaymentResponseDTO rollbackAuthorizeAndCapture(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Passthrough Payment Gateway - Rolling back authorize and capture transaction with amount: " + transactionToBeRolledBack.getTransactionTotal());
        }

        if (transactionToBeRolledBack.getAdditionalFields().containsKey(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)) {

            return new PaymentResponseDTO(
                    PaymentType.getInstance((String)transactionToBeRolledBack.getAdditionalFields().get(PassthroughPaymentConstants.PASSTHROUGH_PAYMENT_TYPE)),
                    PaymentGatewayType.PASSTHROUGH)
                    .rawResponse("rollback authorize and capture - successful")
                    .successful(true)
                    .paymentTransactionType(PaymentTransactionType.VOID)
                    .amount(new Money(transactionToBeRolledBack.getTransactionTotal()));

        }

        throw new PaymentException("Make sure transaction contains a Passthrough Payment Type");
    }

    @Override
    public PaymentResponseDTO rollbackRefund(PaymentRequestDTO transactionToBeRolledBack) throws PaymentException {
        throw new PaymentException("The Rollback Refund method is not supported for this module");
    }
}
