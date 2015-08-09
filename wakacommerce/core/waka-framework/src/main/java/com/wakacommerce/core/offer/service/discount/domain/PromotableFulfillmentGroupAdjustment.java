
package com.wakacommerce.core.offer.service.discount.domain;

import java.io.Serializable;

import com.wakacommerce.common.money.Money;

/**
 *
 * @ hui
 */
public interface PromotableFulfillmentGroupAdjustment extends Serializable {

    public PromotableFulfillmentGroup getPromotableFulfillmentGroup();

    public PromotableCandidateFulfillmentGroupOffer getPromotableCandidateFulfillmentGroupOffer();

    public Money getSaleAdjustmentValue();

    public Money getRetailAdjustmentValue();

    public Money getAdjustmentValue();

    boolean isCombinable();

    boolean isTotalitarian();

    void finalizeAdjustment(boolean useSaleAdjustments);

    boolean isAppliedToSalePrice();
}
