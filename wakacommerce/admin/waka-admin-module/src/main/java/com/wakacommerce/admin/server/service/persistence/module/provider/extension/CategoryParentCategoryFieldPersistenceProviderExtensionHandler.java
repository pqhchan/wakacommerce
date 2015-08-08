package com.wakacommerce.admin.server.service.persistence.module.provider.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.openadmin.dto.Property;

public interface CategoryParentCategoryFieldPersistenceProviderExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType manageParentCategory(Property property, Category category);

}
