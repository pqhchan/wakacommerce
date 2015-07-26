
package com.wakacommerce.admin.server.service.persistence.module.provider.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.openadmin.dto.Property;

/**
 * Extension handler for {@link com.wakacommerce.admin.server.service.persistence.module.provider.ProductParentCategoryFieldPersistenceProvider}
 *
 *Jeff Fischer
 */
public interface ProductParentCategoryFieldPersistenceProviderExtensionHandler extends ExtensionHandler {

    /**
     * Perform any special handling for the parent category of a product
     *
     * @param property
     * @param product
     * @return
     */
    ExtensionResultStatusType manageParentCategory(Property property, Product product);

}
