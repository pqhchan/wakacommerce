
package com.wakacommerce.core.offer.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferItemCriteria;
import com.wakacommerce.core.offer.domain.OrderItemPriceDetailAdjustment;
import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrder;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrderItem;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail;
import com.wakacommerce.core.offer.service.processor.ItemOfferMarkTargets;
import com.wakacommerce.core.offer.service.processor.ItemOfferProcessorImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemPriceDetail;
import com.wakacommerce.core.order.domain.OrderItemQualifier;
import com.wakacommerce.core.order.domain.dto.OrderItemHolder;

/**
 *
 * @ hui
 */
public interface OfferServiceUtilities {

    void sortTargetItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice);

    void sortQualifierItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice);

    OrderItem findRelatedQualifierRoot(OrderItem relatedQualifier);

    public boolean itemOfferCanBeApplied(PromotableCandidateItemOffer itemOffer,
            List<PromotableOrderItemPriceDetail> details);

    int markQualifiersForCriteria(PromotableCandidateItemOffer itemOffer, OfferItemCriteria itemCriteria,
            List<PromotableOrderItemPriceDetail> priceDetails);

    int markTargetsForCriteria(PromotableCandidateItemOffer itemOffer, OrderItem relatedQualifier, boolean checkOnly,
            Offer promotion, OrderItem relatedQualifierRoot, OfferItemCriteria itemCriteria,
            List<PromotableOrderItemPriceDetail> priceDetails, int targetQtyNeeded);

    int markRelatedQualifiersAndTargetsForItemCriteria(PromotableCandidateItemOffer itemOffer, PromotableOrder order,
            OrderItemHolder orderItemHolder, OfferItemCriteria itemCriteria,
            List<PromotableOrderItemPriceDetail> priceDetails, ItemOfferMarkTargets itemOfferMarkTargets);

    void applyAdjustmentsForItemPriceDetails(PromotableCandidateItemOffer itemOffer,
            List<PromotableOrderItemPriceDetail> itemPriceDetails);

    void applyOrderItemAdjustment(PromotableCandidateItemOffer itemOffer, PromotableOrderItemPriceDetail itemPriceDetail);

    List<OrderItem> buildOrderItemList(Order order);

    Map<OrderItem, PromotableOrderItem> buildPromotableItemMap(PromotableOrder promotableOrder);

    Map<Long, OrderItemPriceDetailAdjustment> buildItemDetailAdjustmentMap(OrderItemPriceDetail itemDetail);

    void updatePriceDetail(OrderItemPriceDetail itemDetail, PromotableOrderItemPriceDetail promotableDetail);

    void removeUnmatchedPriceDetails(Map<Long, ? extends OrderItemPriceDetail> unmatchedDetailsMap,
            Iterator<? extends OrderItemPriceDetail> pdIterator);

    void removeUnmatchedQualifiers(Map<Long, ? extends OrderItemQualifier> unmatchedQualifiersMap,
            Iterator<? extends OrderItemQualifier> qIterator);
}
