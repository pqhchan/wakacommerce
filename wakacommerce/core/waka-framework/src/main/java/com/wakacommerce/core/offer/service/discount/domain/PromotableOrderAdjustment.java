
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;

import java.io.Serializable;

public interface PromotableOrderAdjustment extends Serializable {

    /**
     * Returns the associated promotableOrder
     * @return
     */
    public PromotableOrder getPromotableOrder();

    /**
     * Returns the associated promotableCandidateOrderOffer
     * @return
     */
    public Offer getOffer();

    /**
     * Returns the value of this adjustment
     * @return
     */
    public Money getAdjustmentValue();

    /**
     * Returns true if this adjustment represents a combinable offer.
     */
    boolean isCombinable();

    /**
     * Returns true if this adjustment represents a totalitarian offer.
     */
    boolean isTotalitarian();
}
