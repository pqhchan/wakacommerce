
package com.wakacommerce.core.offer.dao;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

@Component("blOfferCodeDaoExtensionManager")
public class OfferCodeDaoExtensionManager extends ExtensionManager<OfferCodeDaoExtensionHandler> {

    public OfferCodeDaoExtensionManager() {
        super(OfferCodeDaoExtensionHandler.class);
    }
}
