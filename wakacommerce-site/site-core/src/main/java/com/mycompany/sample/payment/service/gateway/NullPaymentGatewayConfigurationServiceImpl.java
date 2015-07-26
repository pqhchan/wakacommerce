/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 *Elbert Bautista (elbertbautista)
 */
@Service("blNullPaymentGatewayConfigurationService")
public class NullPaymentGatewayConfigurationServiceImpl implements PaymentGatewayConfigurationService {

    @Resource(name = "blNullPaymentGatewayConfiguration")
    protected NullPaymentGatewayConfiguration configuration;

    @Resource(name = "blNullPaymentGatewayRollbackService")
    protected PaymentGatewayRollbackService rollbackService;

    @Resource(name = "blNullPaymentGatewayWebResponseService")
    protected PaymentGatewayWebResponseService webResponseService;

    @Resource(name = "blNullPaymentGatewayTransparentRedirectService")
    protected PaymentGatewayTransparentRedirectService transparentRedirectService;

    @Resource(name = "blNullPaymentGatewayTransactionService")
    protected PaymentGatewayTransactionService transactionService;

    @Resource(name = "blNullPaymentGatewayTRExtensionHandler")
    protected TRCreditCardExtensionHandler creditCardExtensionHandler;

    @Resource(name = "blNullPaymentGatewayFieldExtensionHandler")
    protected PaymentGatewayFieldExtensionHandler fieldExtensionHandler;

    public PaymentGatewayConfiguration getConfiguration() {
        return configuration;
    }

    public PaymentGatewayTransactionConfirmationService getTransactionConfirmationService() {
        return null;
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
        return null;
    }

    public PaymentGatewayRollbackService getRollbackService() {
        return rollbackService;
    }

    public PaymentGatewayWebResponseService getWebResponseService() {
        return webResponseService;
    }

    public PaymentGatewayTransparentRedirectService getTransparentRedirectService() {
        return transparentRedirectService;
    }

    public PaymentGatewayTransactionService getTransactionService() {
        return transactionService;
    }

    public TRCreditCardExtensionHandler getCreditCardExtensionHandler() {
        return creditCardExtensionHandler;
    }

    public PaymentGatewayFieldExtensionHandler getFieldExtensionHandler() {
        return fieldExtensionHandler;
    }

    public CreditCardTypesExtensionHandler getCreditCardTypesExtensionHandler() {
        return null;
    }

}
