package com.wakacommerce.admin.server.service.extension;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.openadmin.dto.PersistencePackage;

public interface ProductCustomPersistenceHandlerExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType manageParentCategoryForAdd(PersistencePackage persistencePackage, Product product) throws ServiceException;

    ExtensionResultStatusType manageParentCategoryForUpdate(PersistencePackage persistencePackage, Product product) throws ServiceException;

}
