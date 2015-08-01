
package com.wakacommerce.admin.server.service.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * Extension manager for {@link com.wakacommerce.admin.server.service.handler.CategoryCustomPersistenceHandler}
 *
 * 
 */
@Service("blCategoryCustomPersistenceHandlerExtensionManager")
public class CategoryCustomPersistenceHandlerExtensionManager extends ExtensionManager<CategoryCustomPersistenceHandlerExtensionHandler> {

    public CategoryCustomPersistenceHandlerExtensionManager() {
        super(CategoryCustomPersistenceHandlerExtensionHandler.class);
    }

}
