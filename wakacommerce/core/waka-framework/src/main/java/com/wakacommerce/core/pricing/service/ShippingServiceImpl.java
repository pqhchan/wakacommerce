
package com.wakacommerce.core.pricing.service;

import com.wakacommerce.common.vendor.service.exception.FulfillmentPriceException;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.pricing.service.module.ShippingModule;

/**
 * @deprecated Should use the {@link FulfillmentOption} paradigm, implemented in {@link FulfillmentPricingService}
 * @see {@link FulfillmentPricingService}, {@link FulfillmentOption}
 */
@Deprecated
public class ShippingServiceImpl implements ShippingService {

    protected ShippingModule shippingModule;

    @Override
    public FulfillmentGroup calculateShippingForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) throws FulfillmentPriceException {
        FulfillmentGroup group = shippingModule.calculateShippingForFulfillmentGroup(fulfillmentGroup);
        return group;
    }

    public ShippingModule getShippingModule() {
        return shippingModule;
    }

    public void setShippingModule(ShippingModule shippingModule) {
        this.shippingModule = shippingModule;
    }

}
