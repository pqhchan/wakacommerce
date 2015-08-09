
package com.wakacommerce.common.payment.service;

import java.util.List;

import javax.annotation.Nonnull;

import com.wakacommerce.common.payment.PaymentGatewayType;

/**
 *
 * @ hui
 */
public interface PaymentGatewayConfigurationServiceProvider {

    public PaymentGatewayConfigurationService getGatewayConfigurationService(@Nonnull PaymentGatewayType gatewayType);
    
    /*
     * All of the gateway configurations configured in the system.
     */
    public List<PaymentGatewayConfigurationService> getGatewayConfigurationServices();
    
    public void setGatewayConfigurationServices(List<PaymentGatewayConfigurationService> gatewayConfigurationServices);
    
}
