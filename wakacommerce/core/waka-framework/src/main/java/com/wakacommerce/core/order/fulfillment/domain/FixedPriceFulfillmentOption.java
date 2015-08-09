
package com.wakacommerce.core.order.fulfillment.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.pricing.service.fulfillment.provider.FixedPriceFulfillmentPricingProvider;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface FixedPriceFulfillmentOption extends FulfillmentOption, Serializable {
    
    public Money getPrice();
    
    public void setPrice(Money price);
    public BroadleafCurrency getCurrency();

    public void setCurrency(BroadleafCurrency currency);
    
}
