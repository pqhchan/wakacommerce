
package com.wakacommerce.core.order.fulfillment.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.service.type.FulfillmentBandResultAmountType;

/**
 *
 * @ hui
 */
public interface FulfillmentBand extends Serializable {

    public Long getId();

    public void setId(Long id);

    public BigDecimal getResultAmount();

    public void setResultAmount(BigDecimal resultAmount);

    public FulfillmentBandResultAmountType getResultAmountType();

    public void setResultAmountType(FulfillmentBandResultAmountType resultAmountType);

}
