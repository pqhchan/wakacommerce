
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
 *
 * @ hui
 */
public interface FulfillmentWeightBand extends FulfillmentBand {

    public BigDecimal getMinimumWeight();
    
    public void setMinimumWeight(BigDecimal weight);
    
    public BandedWeightFulfillmentOption getOption();

    public void setOption(BandedWeightFulfillmentOption option);
    
    public WeightUnitOfMeasureType getWeightUnitOfMeasure();
    
    public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure);

}
