
package com.wakacommerce.core.catalog.dao;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Jeff Fischer
 */
@Service("blCategoryDaoExtensionManager")
public class CategoryDaoExtensionManager extends ExtensionManager<CategoryDaoExtensionHandler> {

    public CategoryDaoExtensionManager() {
        super(CategoryDaoExtensionHandler.class);
    }

}
