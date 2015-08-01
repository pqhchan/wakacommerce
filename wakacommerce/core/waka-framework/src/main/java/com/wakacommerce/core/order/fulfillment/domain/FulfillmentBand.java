
package com.wakacommerce.core.order.fulfillment.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.service.type.FulfillmentBandResultAmountType;

/**
 * This entity is a collection of properties shared between different band implementations.
 * Out of the box, Broadleaf provides implementations for banded weight and banded price.
 * 
 *  
 * @see {@link FulfillmentPriceBand}, {@link FulfillmentWeightBand}
 */
public interface FulfillmentBand extends Serializable {

    public Long getId();

    public void setId(Long id);

    /**
     * Gets the amount that should be applied to the fulfillment
     * cost for the {@link FulfillmentGroup}. This could be applied as
     * a percentage or as a flat rate, depending on the result of calling
     * {@link #getResultType()}. This is required and should never be null
     * 
     * @return the amount to apply for this band
     */
    public BigDecimal getResultAmount();

    /**
     * Sets the amount that should be applied to the fulfillment cost
     * for this band. This can be either a flat rate or a percentage depending
     * on {@link #getResultType()}.
     * 
     * @param resultAmount - the percentage or flat rate that should be applied
     * as a fulfillment cost for this band
     */
    public void setResultAmount(BigDecimal resultAmount);

    /**
     * Gets how {@link #getResultAmount} should be applied to the fulfillment cost
     * 
     * @return the type of {@link #getResultAmount()} which determines how that value
     * should be calculated into the cost
     */
    public FulfillmentBandResultAmountType getResultAmountType();

    /**
     * Sets how {@link #getResultAmount()} should be applied to the fulfillment cost
     * 
     * @param resultAmountType - how the value from {@link #getResultAmount()} should be
     * applied to the cost of the {@link FulfillmentGroup}
     */
    public void setResultAmountType(FulfillmentBandResultAmountType resultAmountType);

}
