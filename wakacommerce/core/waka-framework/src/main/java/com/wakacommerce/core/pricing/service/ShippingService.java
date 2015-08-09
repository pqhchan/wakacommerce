
package com.wakacommerce.core.pricing.service;

import com.wakacommerce.common.vendor.service.exception.FulfillmentPriceException;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentOption;

/**
 *
 * @ hui
 */
@Deprecated
public interface ShippingService {
    
    public FulfillmentGroup calculateShippingForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) throws FulfillmentPriceException;
    
}
