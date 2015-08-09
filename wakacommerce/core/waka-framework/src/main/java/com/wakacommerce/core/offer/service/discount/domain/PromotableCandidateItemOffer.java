
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferItemCriteria;
import com.wakacommerce.core.offer.service.processor.ItemOfferProcessor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface PromotableCandidateItemOffer extends Serializable {

    public HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateQualifiersMap();

    public void setCandidateQualifiersMap(HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateItemsMap);

    public HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateTargetsMap();

    public void setCandidateTargetsMap(HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateItemsMap);

    public Money getPotentialSavings();

    public void setPotentialSavings(Money savings);

    public Money getPotentialSavingsQtyOne();

    public void setPotentialSavingsQtyOne(Money potentialSavingsQtyOne);

    public boolean hasQualifyingItemCriteria();

    public Money calculateSavingsForOrderItem(PromotableOrderItem orderItem, int qtyToReceiveSavings);

    public int calculateMaximumNumberOfUses();

    public int calculateTargetQuantityForTieredOffer();

    public int calculateMaxUsesForItemCriteria(OfferItemCriteria itemCriteria, Offer promotion);
    
    public int getPriority();
    
    public Offer getOffer();

    public int getUses();

    public void addUse();

    public void resetUses();
    
    public boolean isLegacyOffer();

    public List<PromotableOrderItem> getLegacyCandidateTargets();

    public void setLegacyCandidateTargets(List<PromotableOrderItem> candidateTargets);
}
