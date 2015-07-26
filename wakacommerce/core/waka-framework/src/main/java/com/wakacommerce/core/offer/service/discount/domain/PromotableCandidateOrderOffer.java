
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferItemCriteria;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface PromotableCandidateOrderOffer extends Serializable {

    PromotableOrder getPromotableOrder();

    Offer getOffer();

    Money getPotentialSavings();

    HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateQualifiersMap();
    
    boolean isTotalitarian();

    boolean isCombinable();

    int getPriority();

}
