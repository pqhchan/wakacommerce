
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface PromotableOrder extends Serializable {

    void setOrderSubTotalToPriceWithoutAdjustments();

    void setOrderSubTotalToPriceWithAdjustments();

    List<PromotableOrderItem> getAllOrderItems();

    List<PromotableOrderItem> getDiscountableOrderItems(boolean sortBySalePrice);

    List<PromotableOrderItem> getDiscountableOrderItems();

    List<PromotableFulfillmentGroup> getFulfillmentGroups();

    boolean isHasOrderAdjustments();

    List<PromotableOrderAdjustment> getCandidateOrderAdjustments();

    void addCandidateOrderAdjustment(PromotableOrderAdjustment orderAdjustment);

    void removeAllCandidateOfferAdjustments();

    void removeAllCandidateOrderOfferAdjustments();

    void removeAllCandidateItemOfferAdjustments();

    void removeAllCandidateFulfillmentOfferAdjustments();

    void updateRuleVariables(Map<String, Object> ruleVars);

    Order getOrder();

    boolean isTotalitarianOfferApplied();

    Money calculateOrderAdjustmentTotal();

    Money calculateItemAdjustmentTotal();

    List<PromotableOrderItemPriceDetail> getAllPromotableOrderItemPriceDetails();

    boolean canApplyOrderOffer(PromotableCandidateOrderOffer offer);

    BroadleafCurrency getOrderCurrency();

    void setTotalFufillmentCharges(Money totalFulfillmentCharges);

    Money calculateSubtotalWithoutAdjustments();

    Money calculateSubtotalWithAdjustments();

    boolean isIncludeOrderAndItemAdjustments();

    public boolean isTotalitarianOrderOfferApplied();

    public boolean isTotalitarianItemOfferApplied();

    public boolean isTotalitarianFgOfferApplied();

    Map<String, Object> getExtraDataMap();
}
