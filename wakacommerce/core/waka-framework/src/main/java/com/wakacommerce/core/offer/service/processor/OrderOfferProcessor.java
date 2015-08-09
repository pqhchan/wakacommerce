
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
 * @ hui
 */
public interface OrderOfferProcessor extends BaseProcessor {

    public void filterOrderLevelOffer(PromotableOrder promotableOrder, List<PromotableCandidateOrderOffer> qualifiedOrderOffers, Offer offer);
    
    public Boolean executeExpression(String expression, Map<String, Object> vars);

    public boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder);
    
    public List<PromotableCandidateOrderOffer> removeTrailingNotCombinableOrderOffers(List<PromotableCandidateOrderOffer> candidateOffers);

    public void applyAllOrderOffers(List<PromotableCandidateOrderOffer> orderOffers, PromotableOrder promotableOrder);
    
    public PromotableItemFactory getPromotableItemFactory();

    public void setPromotableItemFactory(PromotableItemFactory promotableItemFactory);

    public void synchronizeAdjustmentsAndPrices(PromotableOrder promotableOrder);

    public void setOfferDao(OfferDao offerDao);

    public void setOrderItemDao(OrderItemDao orderItemDao);
}
