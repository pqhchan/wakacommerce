
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;

import java.io.Serializable;

public interface PromotableOrderAdjustment extends Serializable {

    public PromotableOrder getPromotableOrder();

    public Offer getOffer();

    public Money getAdjustmentValue();

    boolean isCombinable();

    boolean isTotalitarian();
}
