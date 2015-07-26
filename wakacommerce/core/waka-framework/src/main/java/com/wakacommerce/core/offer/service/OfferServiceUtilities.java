
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
 * This class serves to allow reuse of logic between the core offer service and related offer service extensions.
 * 
 * Changes here likely affect other modules including advanced offer and subscription.
 * 
 *bpolster
 *
 */
public interface OfferServiceUtilities {

    /**
     * Used in {@link ItemOfferProcessorImpl#applyItemQualifiersAndTargets(PromotableCandidateItemOffer, PromotableOrder)} 
     * Allow for customized sorting for which qualifier items should be attempted to be used first for a promotion. 
     * 
     * Default behavior is to sort descending, so higher-value items are attempted to be discounted first.
    */
    void sortTargetItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice);

    /**
     * Used in {@link ItemOfferProcessorImpl#applyItemQualifiersAndTargets(PromotableCandidateItemOffer, PromotableOrder)} 
     * Allow for customized sorting for which qualifier items should be attempted to be used first for a promotion. 
     * 
     * Default behavior is to sort descending, so higher-value items are attempted to be qualified first.
    */
    void sortQualifierItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice);

    /**
     * Given an orderItem, finds the top most parent order item.  
     * @param relatedQualifier
     * @return
     */
    OrderItem findRelatedQualifierRoot(OrderItem relatedQualifier);

    /**
     * Return false if a totalitarian or non-combinable offer has already been applied or if this offer is 
     * totalitarian or non-combinable  and this order already has adjustments applied.
     *      
     * @param order
     * @param details
     * @return
     */
    public boolean itemOfferCanBeApplied(PromotableCandidateItemOffer itemOffer,
            List<PromotableOrderItemPriceDetail> details);

    /**
     * Returns the number of qualifiers marked for the passed in itemCriteria    
     * @param itemOffer
     * @param itemCriteria
     * @param priceDetails
     * @return
     */
    int markQualifiersForCriteria(PromotableCandidateItemOffer itemOffer, OfferItemCriteria itemCriteria,
            List<PromotableOrderItemPriceDetail> priceDetails);
    
    /**
     * Returns the number of targets marked for the passed in itemCriteria
     *     
     * @param itemOffer
     * @param relatedQualifier
     * @param checkOnly
     * @param promotion
     * @param relatedQualifierRoot
     * @param itemCriteria
     * @param priceDetails
     * @param targetQtyNeeded
     * @return
     */
    int markTargetsForCriteria(PromotableCandidateItemOffer itemOffer, OrderItem relatedQualifier, boolean checkOnly,
            Offer promotion, OrderItem relatedQualifierRoot, OfferItemCriteria itemCriteria,
            List<PromotableOrderItemPriceDetail> priceDetails, int targetQtyNeeded);

    /**
     * Returns the number of targets marked for the passed in itemCriteria
     * 
     * @param itemOffer
     * @param order
     * @param orderItemHolder
     * @param itemCriteria
     * @param priceDetails
     * @return
     */
    int markRelatedQualifiersAndTargetsForItemCriteria(PromotableCandidateItemOffer itemOffer, PromotableOrder order,
            OrderItemHolder orderItemHolder, OfferItemCriteria itemCriteria,
            List<PromotableOrderItemPriceDetail> priceDetails, ItemOfferMarkTargets itemOfferMarkTargets);

    /**
     * Takes in a list of {@link PromotableOrderItemPriceDetail}s  and applies adjustments for all of the 
     * discounts that match the passed in offer.
     * 
     * @param itemOffer
     * @param itemPriceDetails
     */
    void applyAdjustmentsForItemPriceDetails(PromotableCandidateItemOffer itemOffer,
            List<PromotableOrderItemPriceDetail> itemPriceDetails);
      
    /**
     * Used by applyAdjustments to create an OrderItemAdjustment from a CandidateOrderOffer
     * and associates the OrderItemAdjustment to the OrderItem.
     *
     * @param orderOffer a CandidateOrderOffer to apply to an Order
     */
    void applyOrderItemAdjustment(PromotableCandidateItemOffer itemOffer, PromotableOrderItemPriceDetail itemPriceDetail);

    /**
     * Builds the list of order-items at the level they are being priced which includes splitting bundles that are 
     * being priced at the item level.
     * 
     * @param order
     * @return
     */
    List<OrderItem> buildOrderItemList(Order order);

    /**
     * Builds a map from orderItem to promotableOrderItem.
     * @param promotableOrder
     * @return
     */
    Map<OrderItem, PromotableOrderItem> buildPromotableItemMap(PromotableOrder promotableOrder);

    /**
     * Builds a map from itemDetails for adjustment processing.
     * @param itemDetail
     * @return
     */
    Map<Long, OrderItemPriceDetailAdjustment> buildItemDetailAdjustmentMap(OrderItemPriceDetail itemDetail);


    /**
     * Updates the passed in price detail and its associated adjustments.
     * @param itemDetail
     * @param promotableDetail
     */
    void updatePriceDetail(OrderItemPriceDetail itemDetail, PromotableOrderItemPriceDetail promotableDetail);

    /**
     * Removes price details from the iterator that are contained in the passed in map.
     * @param unmatchedDetailsMap
     * @param pdIterator
     */
    void removeUnmatchedPriceDetails(Map<Long, ? extends OrderItemPriceDetail> unmatchedDetailsMap,
            Iterator<? extends OrderItemPriceDetail> pdIterator);
    
    /**
     * Removes qualifiers from the iterator that are contained in the passed in map.
     * @param unmatchedQualifiersMap
     * @param qIterator
     */
    void removeUnmatchedQualifiers(Map<Long, ? extends OrderItemQualifier> unmatchedQualifiersMap,
            Iterator<? extends OrderItemQualifier> qIterator);
}
