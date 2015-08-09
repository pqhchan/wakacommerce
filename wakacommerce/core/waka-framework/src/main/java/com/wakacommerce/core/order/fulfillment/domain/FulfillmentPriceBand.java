
package com.wakacommerce.core.order.fulfillment.domain;

import java.math.BigDecimal;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.type.FulfillmentBandResultAmountType;

/**
 *
 * @ hui
 */
public interface FulfillmentPriceBand extends FulfillmentBand {

    public BigDecimal getRetailPriceMinimumAmount();

    public void setRetailPriceMinimumAmount(BigDecimal retailPriceMinimumAmount);

    public BandedPriceFulfillmentOption getOption();

    public void setOption(BandedPriceFulfillmentOption option);

}
