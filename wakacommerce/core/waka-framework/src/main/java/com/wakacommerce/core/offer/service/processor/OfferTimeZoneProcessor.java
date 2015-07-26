
package com.wakacommerce.core.offer.service.processor;

import java.util.TimeZone;

import com.wakacommerce.core.offer.domain.Offer;

/**
 * 
 * 
 *
 */
public interface OfferTimeZoneProcessor {

    public TimeZone getTimeZone(Offer offer);
}
