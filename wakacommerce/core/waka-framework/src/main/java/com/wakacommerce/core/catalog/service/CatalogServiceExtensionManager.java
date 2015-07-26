
package com.wakacommerce.core.catalog.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Jeff Fischer
 */
@Service("blCatalogServiceExtensionManager")
public class CatalogServiceExtensionManager extends ExtensionManager<CatalogServiceExtensionHandler> {

    public CatalogServiceExtensionManager() {
        super(CatalogServiceExtensionHandler.class);
    }

}
