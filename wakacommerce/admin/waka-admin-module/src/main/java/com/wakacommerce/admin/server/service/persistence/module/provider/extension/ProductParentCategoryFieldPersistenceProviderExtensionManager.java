
package com.wakacommerce.admin.server.service.persistence.module.provider.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * Extension manager for {@link com.wakacommerce.admin.server.service.persistence.module.provider.ProductParentCategoryFieldPersistenceProvider}
 *
 * 
 */
@Service("blProductParentCategoryFieldPersistenceProviderExtensionManager")
public class ProductParentCategoryFieldPersistenceProviderExtensionManager extends ExtensionManager<ProductParentCategoryFieldPersistenceProviderExtensionHandler> {

    public ProductParentCategoryFieldPersistenceProviderExtensionManager() {
        super(ProductParentCategoryFieldPersistenceProviderExtensionHandler.class);
    }

}
