
package com.wakacommerce.core.offer.domain;


import java.util.List;

import com.wakacommerce.core.offer.service.type.OfferTimeZoneType;

/**
 *
 * @ hui
 */
public interface AdvancedOffer {

    List<OfferTier> getOfferTiers();

    void setOfferTiers(List<OfferTier> offerTiers);

    boolean isTieredOffer();

    void setTieredOffer(boolean isTieredOffer);

    public OfferTimeZoneType getOfferTimeZoneType();

    public void setOfferTimeZoneType(OfferTimeZoneType offerTimeZoneType);


}
