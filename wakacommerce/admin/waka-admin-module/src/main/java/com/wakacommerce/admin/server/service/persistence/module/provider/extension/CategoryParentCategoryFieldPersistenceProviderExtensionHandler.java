
package com.wakacommerce.admin.server.service.persistence.module.provider.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.openadmin.dto.Property;

/**
 * Extension handler for {@link com.wakacommerce.admin.server.service.persistence.module.provider.CategoryParentCategoryFieldPersistenceProvider}
 *
 * 
 */
public interface CategoryParentCategoryFieldPersistenceProviderExtensionHandler extends ExtensionHandler {

    /**
     * Perform any special handling for the parent category of a category
     *
     * @param property
     * @param category
     * @return
     */
    ExtensionResultStatusType manageParentCategory(Property property, Category category);

}
