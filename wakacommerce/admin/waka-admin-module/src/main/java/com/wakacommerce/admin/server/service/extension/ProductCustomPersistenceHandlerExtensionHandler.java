
package com.wakacommerce.admin.server.service.extension;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.openadmin.dto.PersistencePackage;

/**
 * Extension handler for {@link com.wakacommerce.admin.server.service.handler.ProductCustomPersistenceHandler}
 *
 * 
 */
public interface ProductCustomPersistenceHandlerExtensionHandler extends ExtensionHandler {

    /**
     * Perform any special handling for the parent category of a product during a product add
     *
     * @param product
     * @return
     */
    ExtensionResultStatusType manageParentCategoryForAdd(PersistencePackage persistencePackage, Product product) throws ServiceException;

    /**
     * Perform any special handling for the parent category of a product during a product update
     *
     * @param product
     * @return
     */
    ExtensionResultStatusType manageParentCategoryForUpdate(PersistencePackage persistencePackage, Product product) throws ServiceException;
}
