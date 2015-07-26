
package com.wakacommerce.core.pricing.service.module;

import com.wakacommerce.common.vendor.service.exception.FulfillmentPriceException;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider;

/**
 * @deprecated Superceded by functionality given by {@link FulfillmentOption} and {@link FulfillmentPricingProvider}
 * @see {@link FulfillmentPricingProvider}, {@link FulfillmentOption}
 */
@Deprecated
public interface ShippingModule {

    public String getName();

    public void setName(String name);

    public FulfillmentGroup calculateShippingForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) throws FulfillmentPriceException;
    
    public String getServiceName();
    
    public Boolean isValidModuleForService(String serviceName);
    
    public void setDefaultModule(Boolean isDefaultModule);
    
    public Boolean isDefaultModule();

}
