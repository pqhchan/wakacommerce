  
package com.wakacommerce.core.offer.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 * 
 */
public interface OfferOfferRuleXref extends Serializable, MultiTenantCloneable<OfferOfferRuleXref> {

    Long getId();

    void setId(Long id);

    Offer getOffer();

    void setOffer(Offer offer);

    OfferRule getOfferRule();

    void setOfferRule(OfferRule offerRule);

    String getKey();

    void setKey(String key);

}
