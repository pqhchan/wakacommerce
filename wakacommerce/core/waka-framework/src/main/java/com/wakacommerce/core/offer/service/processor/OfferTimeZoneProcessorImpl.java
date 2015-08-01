
package com.wakacommerce.core.offer.service.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.offer.domain.Offer;

import java.util.TimeZone;

/**
 */
@Service("blOfferTimeZoneProcessor")
public class OfferTimeZoneProcessorImpl implements OfferTimeZoneProcessor {

    private static final Log LOG = LogFactory.getLog(OfferTimeZoneProcessorImpl.class);

    public TimeZone getTimeZone(Offer offer) {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        return (brc != null) ? brc.getTimeZone() : TimeZone.getDefault();
    }
}
