
package com.wakacommerce.core.offer.service.processor;

import java.util.List;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateOrderOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrder;

/**
 *
 * @ hui
 */
public interface ItemOfferProcessor extends OrderOfferProcessor {

    public void filterItemLevelOffer(PromotableOrder order, List<PromotableCandidateItemOffer> qualifiedItemOffers, Offer offer);

    public void applyAllItemOffers(List<PromotableCandidateItemOffer> itemOffers, PromotableOrder order);
    
    public void applyAndCompareOrderAndItemOffers(PromotableOrder order, List<PromotableCandidateOrderOffer> qualifiedOrderOffers, List<PromotableCandidateItemOffer> qualifiedItemOffers);
    
    public void filterOffers(PromotableOrder order, List<Offer> filteredOffers, List<PromotableCandidateOrderOffer> qualifiedOrderOffers, List<PromotableCandidateItemOffer> qualifiedItemOffers);

}
