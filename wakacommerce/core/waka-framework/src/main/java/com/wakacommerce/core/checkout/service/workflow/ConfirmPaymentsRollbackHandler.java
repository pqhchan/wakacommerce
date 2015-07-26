/*
 * #%L
 * BroadleafCommerce Framework
 * %%
 * Copyright (C) 2009 - 2014 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.wakacommerce.core.checkout.service.workflow;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayCheckoutService;
import com.wakacommerce.common.payment.service.PaymentGatewayConfigurationService;
import com.wakacommerce.common.payment.service.PaymentGatewayConfigurationServiceProvider;
import com.wakacommerce.common.vendor.service.exception.PaymentException;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.domain.PaymentTransaction;
import com.wakacommerce.core.payment.service.OrderPaymentService;
import com.wakacommerce.core.payment.service.OrderToPaymentRequestDTOService;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.workflow.Activity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.state.RollbackFailureException;
import com.wakacommerce.core.workflow.state.RollbackHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * Rolls back all payments that have been processed or were confirmed in {@link ValidateAndConfirmPaymentActivity}.
 *
 *Phillip Verheyden (phillipuniverse)
 */
@Component("blConfirmPaymentsRollbackHandler")
public class ConfirmPaymentsRollbackHandler implements RollbackHandler<CheckoutSeed> {

    protected static final Log LOG = LogFactory.getLog(ConfirmPaymentsRollbackHandler.class);
    
    @Autowired(required = false)
    @Qualifier("blPaymentGatewayConfigurationServiceProvider")
    protected PaymentGatewayConfigurationServiceProvider paymentConfigurationServiceProvider;
    
    @Resource(name = "blOrderToPaymentRequestDTOService")
    protected OrderToPaymentRequestDTOService transactionToPaymentRequestDTOService;

    @Resource(name = "blOrderPaymentService")
    protected OrderPaymentService orderPaymentService;

    @Resource(name = "blPaymentGatewayCheckoutService")
    protected PaymentGatewayCheckoutService paymentGatewayCheckoutService;

    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity, ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) throws RollbackFailureException {
        CheckoutSeed seed = processContext.getSeedData();

        if (paymentConfigurationServiceProvider == null) {
            throw new RollbackFailureException("There is no rollback service configured for the payment gateway configuration, cannot rollback unconfirmed"
                    + " payments");
        }

        Map<OrderPayment, PaymentTransaction> rollbackResponseTransactions = new HashMap<OrderPayment, PaymentTransaction>();
        Collection<PaymentTransaction> transactions = (Collection<PaymentTransaction>) stateConfiguration.get(ValidateAndConfirmPaymentActivity.ROLLBACK_TRANSACTIONS);
        
        if (CollectionUtils.isNotEmpty(transactions)) {
            for (PaymentTransaction tx : transactions) {
                PaymentRequestDTO rollbackRequest = transactionToPaymentRequestDTOService.translatePaymentTransaction(tx.getAmount(), tx);
                
                PaymentGatewayConfigurationService cfg = paymentConfigurationServiceProvider.getGatewayConfigurationService(tx.getOrderPayment().getGatewayType());
                try {
    
                    PaymentResponseDTO responseDTO = null;
                    if (PaymentTransactionType.AUTHORIZE.equals(tx.getType())) {
                        if (cfg.getRollbackService() != null) {
                            responseDTO = cfg.getRollbackService().rollbackAuthorize(rollbackRequest);
                        }
                    } else if (PaymentTransactionType.AUTHORIZE_AND_CAPTURE.equals(tx.getType())) {
                        if (cfg.getRollbackService() != null) {
                            responseDTO = cfg.getRollbackService().rollbackAuthorizeAndCapture(rollbackRequest);
                        }
                    } else {
                        LOG.warn("The transaction with id " + tx.getId() + " will NOT be rolled back as it is not an AUTHORIZE or AUTHORIZE_AND_CAPTURE transaction but is"
                                + " of type " + tx.getType() + ". If you need to roll back transactions of this type then provide a customized rollback handler for"
                                        + " confirming transactions.");
                    }
    
                    if (responseDTO != null) {
                        PaymentTransaction transaction = orderPaymentService.createTransaction();
                        transaction.setAmount(responseDTO.getAmount());
                        transaction.setRawResponse(responseDTO.getRawResponse());
                        transaction.setSuccess(responseDTO.isSuccessful());
                        transaction.setType(responseDTO.getPaymentTransactionType());
                        transaction.setParentTransaction(tx);
                        transaction.setOrderPayment(tx.getOrderPayment());
                        transaction.setAdditionalFields(responseDTO.getResponseMap());
                        rollbackResponseTransactions.put(tx.getOrderPayment(), transaction);
    
                        if (!responseDTO.isSuccessful()) {
                            LOG.fatal("Unable to rollback transaction with id " + tx.getId() + ". The call was unsuccessful with"
                                    + " raw response: " + responseDTO.getRawResponse());
                        }
                    }
    
    
                } catch (PaymentException e) {
                    throw new RollbackFailureException("The transaction with id " + tx.getId() + " encountered and exception when it was attempted to roll back"
                            + " its confirmation", e);
                }
            }
    
            Order order = seed.getOrder();
            List<OrderPayment> paymentsToInvalidate = new ArrayList<OrderPayment>();
    
            // Add the new rollback transactions to the appropriate payment and mark the payment as invalid.
            // If there was a failed transaction rolling back we will need to throw a RollbackFailureException after saving the
            // Transaction Response to the DB
            boolean rollbackFailure = false;
            for (OrderPayment payment : order.getPayments()) {
                if (rollbackResponseTransactions.containsKey(payment)) {
                    PaymentTransaction rollbackTX = rollbackResponseTransactions.get(payment);
                    payment.addTransaction(rollbackTX);
                    payment = orderPaymentService.save(payment);
                    paymentsToInvalidate.add(payment);
                    if (!rollbackTX.getSuccess()) {
                        rollbackFailure = true;
                    }
                }
            }
    
            for (OrderPayment payment : paymentsToInvalidate) {
                paymentGatewayCheckoutService.markPaymentAsInvalid(payment.getId());
                orderPaymentService.save(payment);
            }
            
            if (rollbackFailure) {
                throw new RollbackFailureException("The ConfirmPaymentsRollbackHandler encountered and exception when it " +
                        "attempted to roll back a transaction on one of the payments. Please see LOG for details.");
            }
    
            try {
                orderService.save(order, false);
            } catch (PricingException e) {
                throw new RollbackFailureException("Unable to save the order with invalidated payments.");
            }
        }
    }

}
