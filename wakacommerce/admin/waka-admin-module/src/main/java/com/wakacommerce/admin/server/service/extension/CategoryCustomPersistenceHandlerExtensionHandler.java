
package com.wakacommerce.admin.server.service.extension;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.openadmin.dto.PersistencePackage;

/**
 * Extension handler for {@link com.wakacommerce.admin.server.service.handler.CategoryCustomPersistenceHandler}
 *
 *Jeff Fischer
 */
public interface CategoryCustomPersistenceHandlerExtensionHandler extends ExtensionHandler {

    /**
     * Perform any special handling for the parent category during a category add
     *
     * @param category
     * @return
     */
    ExtensionResultStatusType manageParentCategoryForAdd(PersistencePackage persistencePackage, Category category) throws ServiceException;

    /**
     * Perform any special handling for the parent category during a category update
     *
     * @param category
     * @return
     */
    ExtensionResultStatusType manageParentCategoryForUpdate(PersistencePackage persistencePackage, Category category) throws ServiceException;
}
