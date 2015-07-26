
package com.wakacommerce.core.offer.service.processor;

import java.util.List;
import java.util.Map;

import com.wakacommerce.core.offer.dao.OfferDao;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateOrderOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableItemFactory;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrder;
import com.wakacommerce.core.order.dao.OrderItemDao;

/**
 * 
 *jfischer
 *
 */
public interface OrderOfferProcessor extends BaseProcessor {

    public void filterOrderLevelOffer(PromotableOrder promotableOrder, List<PromotableCandidateOrderOffer> qualifiedOrderOffers, Offer offer);
    
    public Boolean executeExpression(String expression, Map<String, Object> vars);
    
    /**
     * Executes the appliesToOrderRules in the Offer to determine if this offer
     * can be applied to the Order, OrderItem, or FulfillmentGroup.
     *
     * @param offer
     * @param order
     * @return true if offer can be applied, otherwise false
     */
    public boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder);
    
    public List<PromotableCandidateOrderOffer> removeTrailingNotCombinableOrderOffers(List<PromotableCandidateOrderOffer> candidateOffers);
    
    /**
     * Takes a list of sorted CandidateOrderOffers and determines if each offer can be
     * applied based on the restrictions (stackable and/or combinable) on that offer.  OrderAdjustments
     * are create on the Order for each applied CandidateOrderOffer.  An offer with stackable equals false
     * cannot be applied to an Order that already contains an OrderAdjustment.  An offer with combinable
     * equals false cannot be applied to the Order if the Order already contains an OrderAdjustment.
     *
     * @param orderOffers a sorted list of CandidateOrderOffer
     * @param order       the Order to apply the CandidateOrderOffers
     */
    public void applyAllOrderOffers(List<PromotableCandidateOrderOffer> orderOffers, PromotableOrder promotableOrder);
    
    public PromotableItemFactory getPromotableItemFactory();

    public void setPromotableItemFactory(PromotableItemFactory promotableItemFactory);

    /**
     * Takes the adjustments and PriceDetails from the passed in PromotableOrder and transfers them to the 
     * actual order first checking to see if they already exist.
     * 
     * @param promotableOrder
     */
    public void synchronizeAdjustmentsAndPrices(PromotableOrder promotableOrder);

    /**
     * Set the offerDao (primarily for unit testing)
     */
    public void setOfferDao(OfferDao offerDao);

    /**
     * Set the orderItemDao (primarily for unit testing)
     */
    public void setOrderItemDao(OrderItemDao orderItemDao);
}
