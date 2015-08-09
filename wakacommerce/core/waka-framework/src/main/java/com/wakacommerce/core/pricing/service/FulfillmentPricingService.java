
package com.wakacommerce.core.pricing.service;

import com.wakacommerce.common.vendor.service.exception.FulfillmentPriceException;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.pricing.service.fulfillment.provider.FulfillmentEstimationResponse;
import com.wakacommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider;
import com.wakacommerce.core.pricing.service.workflow.FulfillmentGroupMerchandiseTotalActivity;

import java.util.List;
import java.util.Set;

/**
 *
 * @ hui
 */
public interface FulfillmentPricingService {

    public FulfillmentGroup calculateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) throws FulfillmentPriceException;

    public FulfillmentEstimationResponse estimateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup, Set<FulfillmentOption> options) throws FulfillmentPriceException;
    
    public List<FulfillmentPricingProvider> getProviders();

}
