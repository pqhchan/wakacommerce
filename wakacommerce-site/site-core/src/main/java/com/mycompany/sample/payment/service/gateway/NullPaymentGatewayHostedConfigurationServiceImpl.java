  

package com.mycompany.sample.payment.service.gateway;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.payment.service.PaymentGatewayConfiguration;
import com.wakacommerce.common.payment.service.PaymentGatewayConfigurationService;
import com.wakacommerce.common.payment.service.PaymentGatewayCreditCardService;
import com.wakacommerce.common.payment.service.PaymentGatewayCustomerService;
import com.wakacommerce.common.payment.service.PaymentGatewayFraudService;
import com.wakacommerce.common.payment.service.PaymentGatewayHostedService;
import com.wakacommerce.common.payment.service.PaymentGatewayReportingService;
import com.wakacommerce.common.payment.service.PaymentGatewayRollbackService;
import com.wakacommerce.common.payment.service.PaymentGatewaySubscriptionService;
import com.wakacommerce.common.payment.service.PaymentGatewayTransactionConfirmationService;
import com.wakacommerce.common.payment.service.PaymentGatewayTransactionService;
import com.wakacommerce.common.payment.service.PaymentGatewayTransparentRedirectService;
import com.wakacommerce.common.payment.service.PaymentGatewayWebResponseService;
import com.wakacommerce.common.web.payment.expression.PaymentGatewayFieldExtensionHandler;
import com.wakacommerce.common.web.payment.processor.CreditCardTypesExtensionHandler;
import com.wakacommerce.common.web.payment.processor.TRCreditCardExtensionHandler;

import javax.annotation.Resource;

/**
 *  
 */
@Service("blNullPaymentGatewayHostedConfigurationService")
public class NullPaymentGatewayHostedConfigurationServiceImpl implements PaymentGatewayConfigurationService {

    @Resource(name = "blNullPaymentGatewayHostedConfiguration")
    protected NullPaymentGatewayHostedConfiguration configuration;

    @Resource(name = "blNullPaymentGatewayHostedRollbackService")
    protected PaymentGatewayRollbackService rollbackService;

    @Resource(name = "blNullPaymentGatewayHostedService")
    protected PaymentGatewayHostedService hostedService;

    @Resource(name = "blNullPaymentGatewayHostedTransactionConfirmationService")
    protected PaymentGatewayTransactionConfirmationService transactionConfirmationService;

    @Resource(name = "blNullPaymentGatewayHostedWebResponseService")
    protected PaymentGatewayWebResponseService webResponseService;

    public PaymentGatewayConfiguration getConfiguration() {
        return configuration;
    }

    public PaymentGatewayTransactionService getTransactionService() {
        return null;
    }

    public PaymentGatewayTransactionConfirmationService getTransactionConfirmationService() {
        return transactionConfirmationService;
    }

    public PaymentGatewayReportingService getReportingService() {
        return null;
    }

    public PaymentGatewayCreditCardService getCreditCardService() {
        return null;
    }

    public PaymentGatewayCustomerService getCustomerService() {
        return null;
    }

    public PaymentGatewaySubscriptionService getSubscriptionService() {
        return null;
    }

    public PaymentGatewayFraudService getFraudService() {
        return null;
    }

    public PaymentGatewayHostedService getHostedService() {
        return hostedService;
    }

    public PaymentGatewayRollbackService getRollbackService() {
        return rollbackService;
    }

    public PaymentGatewayWebResponseService getWebResponseService() {
        return webResponseService;
    }

    public PaymentGatewayTransparentRedirectService getTransparentRedirectService() {
        return null;
    }

    public TRCreditCardExtensionHandler getCreditCardExtensionHandler() {
        return null;
    }

    public PaymentGatewayFieldExtensionHandler getFieldExtensionHandler() {
        return null;
    }

    public CreditCardTypesExtensionHandler getCreditCardTypesExtensionHandler() {
        return null;
    }

}
