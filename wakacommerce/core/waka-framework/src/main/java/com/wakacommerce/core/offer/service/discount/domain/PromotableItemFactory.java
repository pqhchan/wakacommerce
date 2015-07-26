
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;

public interface PromotableItemFactory {

    PromotableOrder createPromotableOrder(Order order, boolean includeOrderAndItemAdjustments);

    PromotableCandidateOrderOffer createPromotableCandidateOrderOffer(PromotableOrder promotableOrder, Offer offer);

    PromotableCandidateOrderOffer createPromotableCandidateOrderOffer(PromotableOrder promotableOrder,
            Offer offer, Money potentialSavings);

    PromotableOrderAdjustment createPromotableOrderAdjustment(
            PromotableCandidateOrderOffer promotableCandidateOrderOffer,
            PromotableOrder order);

    PromotableOrderAdjustment createPromotableOrderAdjustment(
            PromotableCandidateOrderOffer promotableCandidateOrderOffer,
            PromotableOrder order, Money value);

    PromotableOrderItem createPromotableOrderItem(OrderItem orderItem, PromotableOrder order,
            boolean includeAdjustments);

    PromotableOrderItemPriceDetail createPromotableOrderItemPriceDetail(PromotableOrderItem promotableOrderItem,
            int quantity);

    PromotableCandidateItemOffer createPromotableCandidateItemOffer(PromotableOrder promotableOrder, Offer offer);

    PromotableOrderItemPriceDetailAdjustment createPromotableOrderItemPriceDetailAdjustment(
            PromotableCandidateItemOffer promotableCandidateItemOffer,
            PromotableOrderItemPriceDetail promotableOrderItemPriceDetail);

    PromotableFulfillmentGroup createPromotableFulfillmentGroup(FulfillmentGroup fulfillmentGroup, PromotableOrder order);

    PromotableCandidateFulfillmentGroupOffer createPromotableCandidateFulfillmentGroupOffer(
            PromotableFulfillmentGroup fulfillmentGroup,
            Offer offer);
    
    PromotableFulfillmentGroupAdjustment createPromotableFulfillmentGroupAdjustment(
            PromotableCandidateFulfillmentGroupOffer promotableCandidateFulfillmentGroupOffer,
            PromotableFulfillmentGroup fulfillmentGroup);
}
