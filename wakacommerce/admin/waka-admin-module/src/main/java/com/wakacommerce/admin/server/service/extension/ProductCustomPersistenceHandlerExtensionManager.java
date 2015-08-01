
package com.wakacommerce.admin.server.service.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * Extension manager for {@link com.wakacommerce.admin.server.service.handler.ProductCustomPersistenceHandler}
 *
 * 
 */
@Service("blProductCustomPersistenceHandlerExtensionManager")
public class ProductCustomPersistenceHandlerExtensionManager extends ExtensionManager<ProductCustomPersistenceHandlerExtensionHandler> {

    public ProductCustomPersistenceHandlerExtensionManager() {
        super(ProductCustomPersistenceHandlerExtensionHandler.class);
    }

}
