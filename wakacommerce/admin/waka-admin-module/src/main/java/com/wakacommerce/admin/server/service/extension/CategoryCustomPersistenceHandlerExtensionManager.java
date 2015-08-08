package com.wakacommerce.admin.server.service.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blCategoryCustomPersistenceHandlerExtensionManager")
public class CategoryCustomPersistenceHandlerExtensionManager extends ExtensionManager<CategoryCustomPersistenceHandlerExtensionHandler> {

    public CategoryCustomPersistenceHandlerExtensionManager() {
        super(CategoryCustomPersistenceHandlerExtensionHandler.class);
    }

}
