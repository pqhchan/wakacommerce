
package com.wakacommerce.core.order.fulfillment.domain;

import com.wakacommerce.common.util.WeightUnitOfMeasureType;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.type.FulfillmentBandResultAmountType;

import java.math.BigDecimal;

/**
 * <p>This entity defines the bands that can be specified for {@link BandedWeightFulfillmentOption}. Bands
 * work on the cumulated weight of an {@link Order} and should be calculated as follows:</p>
 * <ol>
 *  <li>The weight of all of the {@link OrderItem}s (via the relationship to {@link Sku}) in a {@link FulfillmentGroup} (which
 *  is obtained through their relationship with {@link FulfillmentGroupItem} are summed together</li>
 *  <li>The {@link FulfillmentWeightBand} should be looked up by getting the closest band less
 *  than the sum of the weights</li>
 *  <li>If {@link #getResultAmountType()} returns {@link FulfillmentBandResultAmountType#RATE}, then
 *  the cost for the fulfillment group is whatever is defined in {@link #getResultAmount()}</li>
 *  <li>If {@link #getResultAmountType()} returns {@link FulfillmentBandResultAmountType#PERCENTAGE}, then
 *  the fulfillment cost is the percentage obtained by {@link #getResultAmount()} * retailPriceTotal</li>
 *  <li>If two bands have the same weight, the cheapest resulting amount is used</li>
 * </ol>
 * <p>Note: this implementation assumes that units of measurement (lb, kg, etc) are the same across the site implementation</p>
 *
 *Phillip Verheyden
 * 
 */
public interface FulfillmentWeightBand extends FulfillmentBand {

    public BigDecimal getMinimumWeight();
    
    public void setMinimumWeight(BigDecimal weight);
    
    public BandedWeightFulfillmentOption getOption();

    public void setOption(BandedWeightFulfillmentOption option);
    
    public WeightUnitOfMeasureType getWeightUnitOfMeasure();
    
    public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure);

}
