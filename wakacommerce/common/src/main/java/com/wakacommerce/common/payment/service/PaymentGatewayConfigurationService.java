

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.web.payment.expression.PaymentGatewayFieldExtensionHandler;
import com.wakacommerce.common.web.payment.processor.CreditCardTypesExtensionHandler;
import com.wakacommerce.common.web.payment.processor.TRCreditCardExtensionHandler;

/**
 *
 * @ hui
 */
public interface PaymentGatewayConfigurationService {

    public PaymentGatewayConfiguration getConfiguration();

    public PaymentGatewayTransactionService getTransactionService();

    public PaymentGatewayTransactionConfirmationService getTransactionConfirmationService();

    public PaymentGatewayReportingService getReportingService();

    public PaymentGatewayCreditCardService getCreditCardService();

    public PaymentGatewayCustomerService getCustomerService();

    public PaymentGatewaySubscriptionService getSubscriptionService();

    public PaymentGatewayFraudService getFraudService();

    public PaymentGatewayHostedService getHostedService();

    public PaymentGatewayRollbackService getRollbackService();

    public PaymentGatewayWebResponseService getWebResponseService();

    public PaymentGatewayTransparentRedirectService getTransparentRedirectService();

    public TRCreditCardExtensionHandler getCreditCardExtensionHandler();

    public PaymentGatewayFieldExtensionHandler getFieldExtensionHandler();

    public CreditCardTypesExtensionHandler getCreditCardTypesExtensionHandler();

}
