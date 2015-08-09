
package com.wakacommerce.core.offer.service.processor;

import java.util.List;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.service.discount.FulfillmentGroupOfferPotential;
import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrder;

/**
 *
 * @ hui
 */
public interface FulfillmentGroupOfferProcessor extends OrderOfferProcessor {

    public void filterFulfillmentGroupLevelOffer(PromotableOrder order, List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers, Offer offer);

    public void calculateFulfillmentGroupTotal(PromotableOrder order);

    public boolean applyAllFulfillmentGroupOffers(List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers, PromotableOrder order);
    
    public List<FulfillmentGroupOfferPotential> removeTrailingNotCombinableFulfillmentGroupOffers(List<FulfillmentGroupOfferPotential> candidateOffers);
    
}
