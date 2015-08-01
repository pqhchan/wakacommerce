  

package com.mycompany.sample.payment.service.gateway;

import com.wakacommerce.common.payment.service.PaymentGatewayConfiguration;

/**
 *  
 */
public interface NullPaymentGatewayHostedConfiguration extends PaymentGatewayConfiguration {

    public String getHostedRedirectUrl();

    public String getHostedRedirectReturnUrl();

}
