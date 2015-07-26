package com.mycompany.sample.payment.service.gateway;

import com.wakacommerce.common.payment.service.PaymentGatewayConfiguration;

public interface NullPaymentGatewayConfiguration extends PaymentGatewayConfiguration {

	public String getTransparentRedirectUrl();

	public String getTransparentRedirectReturnUrl();

}
