
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferItemCriteria;

import java.util.HashMap;
import java.util.List;

public interface PromotableCandidateFulfillmentGroupOffer {

    public HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateQualifiersMap();

    public void setCandidateQualifiersMap(HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateItemsMap);

    public Money computeDiscountedAmount();

    public Money getDiscountedPrice();
    
    public Offer getOffer();
    
    public PromotableFulfillmentGroup getFulfillmentGroup();

    public Money getDiscountedAmount();
}
