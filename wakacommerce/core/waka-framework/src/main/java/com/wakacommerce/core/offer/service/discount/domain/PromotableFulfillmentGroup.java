
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface PromotableFulfillmentGroup extends Serializable {

    void addCandidateFulfillmentGroupAdjustment(PromotableFulfillmentGroupAdjustment adjustment);

    List<PromotableFulfillmentGroupAdjustment> getCandidateFulfillmentGroupAdjustments();

    void removeAllCandidateAdjustments();

    void chooseSaleOrRetailAdjustments();

    public FulfillmentGroup getFulfillmentGroup();

    void updateRuleVariables(Map<String, Object> ruleVars);

    public Money calculatePriceWithAdjustments(boolean useSalePrice);

    public Money getFinalizedPriceWithAdjustments();

    List<PromotableOrderItem> getDiscountableOrderItems();

    boolean canApplyOffer(PromotableCandidateFulfillmentGroupOffer fulfillmentGroupOffer);

    Money calculatePriceWithoutAdjustments();

    boolean isTotalitarianOfferApplied();

}
