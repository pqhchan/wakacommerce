
package com.wakacommerce.core.offer.domain;


import java.util.List;

import com.wakacommerce.core.offer.service.type.OfferTimeZoneType;

/**
 * Add advanced offer support to an Offer
 * 
 *Priyesh Patel
 */
public interface AdvancedOffer {

    /**
     * List of Tiers an offer supports.   Implemented in external module.
     * @return
     */
    List<OfferTier> getOfferTiers();

    /**
     * Sets the list of Tiers.
     * @param offerTiers
     */
    void setOfferTiers(List<OfferTier> offerTiers);

    /**
     * Returns true if this is a tiered offer meaning that the amount depends on the
     * quantity being purchased.
     * @return
     */
    boolean isTieredOffer();

    /**
     * Sets whether or not this is a tiered offer.
     * @param isTieredOffer
     */
    void setTieredOffer(boolean isTieredOffer);
    
    /**
     * Sets the {@link OfferTimeZoneType} 
     * @return
     */
    public OfferTimeZoneType getOfferTimeZoneType();

    /**
     * Returns the {@link OfferTimeZoneType}
     * @param offerTimeZoneType
     */
    public void setOfferTimeZoneType(OfferTimeZoneType offerTimeZoneType);


}
