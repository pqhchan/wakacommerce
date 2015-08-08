package com.wakacommerce.admin.server.service.extension;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.openadmin.dto.PersistencePackage;

public interface CategoryCustomPersistenceHandlerExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType manageParentCategoryForAdd(PersistencePackage persistencePackage, Category category) throws ServiceException;

    ExtensionResultStatusType manageParentCategoryForUpdate(PersistencePackage persistencePackage, Category category) throws ServiceException;

}
