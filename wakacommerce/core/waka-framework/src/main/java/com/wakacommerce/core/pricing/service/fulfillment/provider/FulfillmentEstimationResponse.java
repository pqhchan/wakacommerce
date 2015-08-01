
package com.wakacommerce.core.pricing.service.fulfillment.provider;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.pricing.service.FulfillmentPricingService;

import java.util.Map;

/**
 * DTO to allow FulfillmentProcessors to respond to estimation requests for a particular FulfillmentGroup
 * for a particular FulfillmentOptions
 * 
 *  
 * @see {@link FulfillmentPricingProvider}, {@link FulfillmentPricingService}
 */
public class FulfillmentEstimationResponse {

    protected Map<? extends FulfillmentOption, Money> fulfillmentOptionPrices;

    public Map<? extends FulfillmentOption, Money> getFulfillmentOptionPrices() {
        return fulfillmentOptionPrices;
    }

    public void setFulfillmentOptionPrices(Map<? extends FulfillmentOption, Money> fulfillmentOptionPrices) {
        this.fulfillmentOptionPrices = fulfillmentOptionPrices;
    }
}
