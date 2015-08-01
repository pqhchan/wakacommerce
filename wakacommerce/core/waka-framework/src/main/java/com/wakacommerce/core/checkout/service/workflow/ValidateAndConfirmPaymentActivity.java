

package com.wakacommerce.core.checkout.service.workflow;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayCheckoutService;
import com.wakacommerce.common.payment.service.PaymentGatewayConfigurationService;
import com.wakacommerce.common.payment.service.PaymentGatewayConfigurationServiceProvider;
import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.core.checkout.service.exception.CheckoutException;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.domain.PaymentTransaction;
import com.wakacommerce.core.payment.domain.secure.CreditCardPayment;
import com.wakacommerce.core.payment.service.OrderPaymentService;
import com.wakacommerce.core.payment.service.OrderToPaymentRequestDTOService;
import com.wakacommerce.core.payment.service.SecureOrderPaymentService;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.WorkflowException;
import com.wakacommerce.core.workflow.state.ActivityStateManagerImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * <p>Verifies that there is enough payment on the order via the <i>successful</i> amount on {@link PaymentTransactionType.AUTHORIZE} and
 * {@link PaymentTransactionType.AUTHORIZE_AND_CAPTURE} transactions. This will also confirm any {@link PaymentTransactionType.UNCONFIRMED} transactions
 * that exist on am {@link OrderPayment}.</p>
 * 
 * <p>If there is an exception (either in this activity or later downstream) the confirmed payments are rolled back via {@link ConfirmPaymentsRollbackHandler}
 *
 *     
 */
public class ValidateAndConfirmPaymentActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {
    
    protected static final Log LOG = LogFactory.getLog(ValidateAndConfirmPaymentActivity.class);
    
    /**
     * <p>
     * Used by the {@link ConfirmPaymentsRollbackHandler} to roll back transactions that this activity confirms.
     * 
     * <p>
     * This could also contain failed transactions that still need to be rolled back
     */
    public static final String ROLLBACK_TRANSACTIONS = "confirmedTransactions";
    
    @Autowired(required = false)
    @Qualifier("blPaymentGatewayConfigurationServiceProvider")
    protected PaymentGatewayConfigurationServiceProvider paymentConfigurationServiceProvider;
    
    @Resource(name = "blOrderToPaymentRequestDTOService")
    protected OrderToPaymentRequestDTOService orderToPaymentRequestService;

    @Resource(name = "blOrderPaymentService")
    protected OrderPaymentService orderPaymentService;

    @Resource(name = "blSecureOrderPaymentService")
    protected SecureOrderPaymentService secureOrderPaymentService;
    
    @Resource(name = "blPaymentGatewayCheckoutService")
    protected PaymentGatewayCheckoutService paymentGatewayCheckoutService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();
        
        Map<String, Object> rollbackState = new HashMap<String, Object>(); 
        
        // There are definitely enough payments on the order. We now need to confirm each unconfirmed payment on the order.
        // Unconfirmed payments could be added for things like gift cards and account credits; they are not actually
        // decremented from the user's account until checkout. This could also be used in some credit card processing
        // situations
        // Important: The payment.getAmount() must be the final amount that is going to be confirmed. If the order total
        // changed, the order payments need to be adjusted to reflect this and must add up to the order total.
        // This can happen in the case of PayPal Express or other hosted gateways where the unconfirmed payment comes back
        // to a review page, the customer selects shipping and the order total is adjusted.
        
        /**
         * This list contains the additional transactions that were created to confirm previously unconfirmed transactions
         * which can occur if you send credit card data directly to Broadlaef and rely on this activity to confirm
         * that transaction
         */
        Map<OrderPayment, PaymentTransaction> additionalTransactions = new HashMap<OrderPayment, PaymentTransaction>();
        List<ResponseTransactionPair> failedTransactions = new ArrayList<ResponseTransactionPair>();
        // Used for the rollback handler; we want to make sure that we roll back transactions that have already been confirmed
        // as well as transctions that we are about to confirm here
        List<PaymentTransaction> confirmedTransactions = new ArrayList<PaymentTransaction>();
        /**
         * This is a subset of the additionalTransactions that contains the transactions that were confirmed in this activity
         */
        Map<OrderPayment, PaymentTransactionType> additionalConfirmedTransactions = new HashMap<OrderPayment, PaymentTransactionType>();

        for (OrderPayment payment : order.getPayments()) {
            if (payment.isActive()) {
                for (PaymentTransaction tx : payment.getTransactions()) {
                    if (PaymentTransactionType.UNCONFIRMED.equals(tx.getType())) {
                        if (LOG.isTraceEnabled()) {
                            LOG.trace("Transaction " + tx.getId() + " is not confirmed. Proceeding to confirm transaction.");
                        }

                        // Cannot confirm anything here if there is no provider
                        if (paymentConfigurationServiceProvider == null) {
                            String msg = "There are unconfirmed payment transactions on this payment but no payment gateway" +
                                    " configuration or transaction confirmation service configured";
                            LOG.error(msg);
                            throw new CheckoutException(msg, context.getSeedData());
                        }

                        PaymentGatewayConfigurationService cfg = paymentConfigurationServiceProvider.getGatewayConfigurationService(tx.getOrderPayment().getGatewayType());
                        PaymentResponseDTO responseDTO = null;

                        if (PaymentType.CREDIT_CARD.equals(payment.getType())) {
                            // Handles the PCI-Compliant Scenario where you have an UNCONFIRMED CREDIT_CARD payment on the order.
                            // This can happen if you send the Credit Card directly to Broadleaf or you use a Digital Wallet solution like MasterPass.
                            // The Actual Credit Card PAN is stored in blSecurePU and will need to be sent to the Payment Gateway for processing.

                            PaymentRequestDTO s2sRequest = orderToPaymentRequestService.translatePaymentTransaction(payment.getAmount(), tx);
                            populateCreditCardOnRequest(s2sRequest, payment);
                            populateBillingAddressOnRequest(s2sRequest, payment);
                            populateCustomerOnRequest(s2sRequest, payment);
                            populateShippingAddressOnRequest(s2sRequest, payment);

                            if (cfg.getConfiguration().isPerformAuthorizeAndCapture()) {
                                responseDTO = cfg.getTransactionService().authorizeAndCapture(s2sRequest);
                            } else {
                                responseDTO = cfg.getTransactionService().authorize(s2sRequest);
                            }

                        } else {
                            // This handles the THIRD_PARTY_ACCOUNT scenario (like PayPal Express Checkout) where
                            // the transaction just needs to be confirmed with the Gateway

                            responseDTO = cfg.getTransactionConfirmationService()
                                .confirmTransaction(orderToPaymentRequestService.translatePaymentTransaction(payment.getAmount(), tx));
                        }

                        if (responseDTO == null) {
                            String msg = "Unable to Confirm/Authorize the UNCONFIRMED Transaction with id: " + tx.getId() + ". " +
                                    "The ResponseDTO returned from the Gateway was null. Please check your implementation";
                            LOG.error(msg);
                            throw new CheckoutException(msg, context.getSeedData());
                        }

                        if (LOG.isTraceEnabled()) {
                            LOG.trace("Transaction Confirmation Raw Response: " +  responseDTO.getRawResponse());
                        }

                        if (responseDTO.getAmount() == null || responseDTO.getPaymentTransactionType() == null) {
                            //Log an error, an exception will get thrown later as the payments won't add up.
                            LOG.error("The ResponseDTO returned from the Gateway does not contain either an Amount or Payment Transaction Type. " +
                                    "Please check your implementation");
                        }

                        // Create a new transaction that references its parent UNCONFIRMED transaction.
                        PaymentTransaction transaction = orderPaymentService.createTransaction();
                        transaction.setAmount(responseDTO.getAmount());
                        transaction.setRawResponse(responseDTO.getRawResponse());
                        transaction.setSuccess(responseDTO.isSuccessful());
                        transaction.setType(responseDTO.getPaymentTransactionType());
                        transaction.setParentTransaction(tx);
                        transaction.setOrderPayment(payment);
                        transaction.setAdditionalFields(responseDTO.getResponseMap());
                        transaction = orderPaymentService.save(transaction);
                        additionalTransactions.put(payment, transaction);

                        if (responseDTO.isSuccessful()) {
                            additionalConfirmedTransactions.put(payment, transaction.getType());
                        } else {
                            failedTransactions.add(new ResponseTransactionPair(responseDTO, transaction.getId()));
                        }

                    } else if (PaymentTransactionType.AUTHORIZE.equals(tx.getType()) ||
                            PaymentTransactionType.AUTHORIZE_AND_CAPTURE.equals(tx.getType())) {
                        // After each transaction is confirmed, associate the new list of confirmed transactions to the rollback state. This has the added
                        // advantage of being able to invoke the rollback handler if there is an exception thrown at some point while confirming multiple
                        // transactions. This is outside of the transaction confirmation block in order to capture transactions
                        // that were already confirmed prior to this activity running
                        confirmedTransactions.add(tx);
                    }
                }
            }
        }
        
        // Add the new transactions to this payment (failed and confirmed) These need to be saved on the order payment
        // regardless of an error in the workflow later.
        for (OrderPayment payment : order.getPayments()) {
            if (additionalTransactions.containsKey(payment)) {
                PaymentTransactionType confirmedType = null;
                if (additionalConfirmedTransactions.containsKey(payment)) {
                    confirmedType = additionalConfirmedTransactions.get(payment);
                }

                payment.addTransaction(additionalTransactions.get(payment));
                payment = orderPaymentService.save(payment);

                if (confirmedType != null) {
                    List<PaymentTransaction> types = payment.getTransactionsForType(confirmedType);
                    if (types.size() == 1) {
                        confirmedTransactions.add(types.get(0));
                    } else {
                        throw new IllegalArgumentException("There should only be one AUTHORIZE or AUTHORIZE_AND_CAPTURE transaction." +
                                "There are more than one confirmed payment transactions for Order Payment:" + payment.getId() );
                    }
                }
            }
        }

        // Once all transactions have been confirmed, add them to the rollback state.
        // If an exception is thrown after this, the confirmed transactions will need to be voided or reversed
        // (based on the implementation requirements of the Gateway)
        rollbackState.put(ROLLBACK_TRANSACTIONS, confirmedTransactions);
        ActivityStateManagerImpl.getStateManager().registerState(this, context, getRollbackHandler(), rollbackState);

        //Handle the failed transactions (default implementation is to throw a new CheckoutException)
        if (!failedTransactions.isEmpty()) {
            handleUnsuccessfulTransactions(failedTransactions, context);
        }

        // Add authorize and authorize_and_capture; there should only be one or the other in the payment
        Money paymentSum = new Money(BigDecimal.ZERO);
        for (OrderPayment payment : order.getPayments()) {
            if (payment.isActive()) {
                paymentSum = paymentSum.add(payment.getSuccessfulTransactionAmountForType(PaymentTransactionType.AUTHORIZE))
                               .add(payment.getSuccessfulTransactionAmountForType(PaymentTransactionType.AUTHORIZE_AND_CAPTURE));
            }
        }
        
        if (paymentSum.lessThan(order.getTotal())) {
            throw new IllegalArgumentException("There are not enough payments to pay for the total order. The sum of " + 
                    "the payments is " + paymentSum.getAmount().toPlainString() + " and the order total is " + order.getTotal().getAmount().toPlainString());
        }
        
        // There should also likely be something that says whether the payment was successful or not and this should check
        // that as well. Currently there isn't really a concept for that
        return context;
    }

    /**
     * <p>
     * Default implementation is to throw a generic CheckoutException which will be caught and displayed
     * on the Checkout Page where the Customer can try again. In many cases, this is
     * sufficient as it is usually recommended to display a generic Error Message to prevent
     * Credit Card fraud.
     *
     * <p>
     * The configured payment gateway may return a more specific error.
     * Each gateway is different and will often times return different error codes based on the acquiring bank as well.
     * In that case, you may override this method to decipher these errors
     * and handle it appropriately based on your business requirements.
     *
     * @param responseDTOs
     */
    protected void handleUnsuccessfulTransactions(List<ResponseTransactionPair> failedTransactions, ProcessContext<CheckoutSeed> context) throws Exception {
        //The Response DTO was not successful confirming/authorizing a transaction.
        String msg = "Attempting to confirm/authorize an UNCONFIRMED transaction on the order was unsuccessful.";
        
        
        /**
         * For each of the failed transactions we might need to register state with the rollback handler
         */
        List<OrderPayment> invalidatedPayments = new ArrayList<OrderPayment>();
        List<PaymentTransaction> failedTransactionsToRollBack = new ArrayList<PaymentTransaction>();
        for (ResponseTransactionPair responseTransactionPair : failedTransactions) {
            PaymentTransaction tx = orderPaymentService.readTransactionById(responseTransactionPair.getTransactionId());
            if (shouldRollbackFailedTransaction(responseTransactionPair)) {
                failedTransactionsToRollBack.add(tx);
            } else if (!invalidatedPayments.contains(tx.getOrderPayment())) {
                paymentGatewayCheckoutService.markPaymentAsInvalid(tx.getOrderPayment().getId());
                OrderPayment payment = orderPaymentService.save(tx.getOrderPayment());
                invalidatedPayments.add(payment);
            }
        }
        
        /**
         * Even though the original transaction confirmation failed, there is still a possibility that we need to rollback
         * the failure. The use case is in the case of fraud checks, some payment gateways complete the AUTHORIZE prior to
         * executing the fraud check. Thus, the AUTHORIZE technically fails because of fraud but the user's card was still
         * charged. This handles the case of rolling back the AUTHORIZE transaction in that case
         */
        Map<String, Object> rollbackState = new HashMap<String, Object>(); 
        rollbackState.put(ROLLBACK_TRANSACTIONS, failedTransactionsToRollBack);
        ActivityStateManagerImpl.getStateManager().registerState(this, context, getRollbackHandler(), rollbackState);
        
        if (LOG.isErrorEnabled()) {
            LOG.error(msg);
        }

        if (LOG.isTraceEnabled()) {
            for (ResponseTransactionPair responseTransactionPair : failedTransactions) {
                LOG.trace(responseTransactionPair.getResponseDTO().getRawResponse());
            }
        }

        throw new CheckoutException(msg, context.getSeedData());
    }
    
    protected boolean shouldRollbackFailedTransaction(ResponseTransactionPair failedTransactionPair) {
        return false;
    }

    protected void populateCreditCardOnRequest(PaymentRequestDTO requestDTO, OrderPayment payment) throws WorkflowException {

        if (payment.getReferenceNumber() != null) {
            CreditCardPayment creditCardPayment = (CreditCardPayment) secureOrderPaymentService.findSecurePaymentInfo(payment.getReferenceNumber(), PaymentType.CREDIT_CARD);
            if (creditCardPayment != null) {
                requestDTO.creditCard()
                        .creditCardHolderName(creditCardPayment.getNameOnCard())
                        .creditCardNum(creditCardPayment.getPan())
                        .creditCardExpDate(
                                constructExpirationDate(creditCardPayment.getExpirationMonth(),
                                        creditCardPayment.getExpirationYear()))
                        .creditCardExpMonth(creditCardPayment.getExpirationMonth() + "")
                        .creditCardExpYear(creditCardPayment.getExpirationYear() + "")
                        .done();
            }
        }
    }

    protected void populateBillingAddressOnRequest(PaymentRequestDTO requestDTO, OrderPayment payment) {

        if (payment != null && payment.getBillingAddress() != null) {
            orderToPaymentRequestService.populateBillTo(payment.getOrder(), requestDTO);
        }

    }

    protected void populateCustomerOnRequest(PaymentRequestDTO requestDTO, OrderPayment payment) {
        if (payment != null && payment.getOrder() != null && payment.getOrder().getCustomer() != null) {
            orderToPaymentRequestService.populateCustomerInfo(payment.getOrder(), requestDTO);
        }

    }
    
    protected void populateShippingAddressOnRequest(PaymentRequestDTO requestDTO, OrderPayment payment) {
        if(payment != null && payment.getOrder() != null) {
            orderToPaymentRequestService.populateShipTo(payment.getOrder(), requestDTO);
        }
    }

    /**
     * Default expiration date construction.
     * Some Payment Gateways may require a different format. Override if necessary or set the property
     * "gateway.config.global.expDateFormat" with a format string to provide the correct format for the configured gateway.
     * @param expMonth
     * @param expYear
     * @return
     */
    protected String constructExpirationDate(Integer expMonth, Integer expYear) {
        SimpleDateFormat sdf = new SimpleDateFormat(getGatewayExpirationDateFormat());
        DateTime exp = new DateTime()
                .withYear(expYear)
                .withMonthOfYear(expMonth);

        return sdf.format(exp.toDate());
    }

    protected String getGatewayExpirationDateFormat(){
        String format = BLCSystemProperty.resolveSystemProperty("gateway.config.global.expDateFormat");
        if (StringUtils.isBlank(format)) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("The System Property 'gateway.config.global.expDateFormat' is not set. " +
                        "Defaulting to the format 'MM/YY' for the configured gateway.");
            }
            format = "MM/YY";
        }
        return format;
    }
    
    protected class ResponseTransactionPair {
        PaymentResponseDTO responseDTO;
        Long transactionId;
        
        ResponseTransactionPair() {
            this(null, null);
        }
        ResponseTransactionPair(PaymentResponseDTO responseDTO, Long transactionId) {
            this.responseDTO = responseDTO;
            this.transactionId = transactionId;
        }
        
        public PaymentResponseDTO getResponseDTO() {
            return responseDTO;
        }
        
        public Long getTransactionId() {
            return transactionId;
        }
    }

}
