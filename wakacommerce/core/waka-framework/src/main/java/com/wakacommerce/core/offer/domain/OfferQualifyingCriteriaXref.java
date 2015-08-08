  
package com.wakacommerce.core.offer.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 *  
 */
public interface OfferQualifyingCriteriaXref extends Serializable, MultiTenantCloneable<OfferQualifyingCriteriaXref> {

    Long getId();

    void setId(Long id);

    Offer getOffer();

    void setOffer(Offer offer);

    OfferItemCriteria getOfferItemCriteria();

    void setOfferItemCriteria(OfferItemCriteria offerItemCriteria);

}
