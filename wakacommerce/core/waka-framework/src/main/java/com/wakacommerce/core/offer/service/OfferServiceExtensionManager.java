
package com.wakacommerce.core.offer.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *Andre Azzolini (apazzolini), bpolster
 */
@Service("blOfferServiceExtensionManager")
public class OfferServiceExtensionManager extends ExtensionManager<OfferServiceExtensionHandler> {

    public static final String STOP_PROCESSING = "stopProcessing";

    public OfferServiceExtensionManager() {
        super(OfferServiceExtensionHandler.class);
    }

}
