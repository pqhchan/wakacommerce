
package com.wakacommerce.core.pricing.service.fulfillment.provider;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.pricing.service.FulfillmentPricingService;

import java.util.Map;

/**
 *
 * @ hui
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
