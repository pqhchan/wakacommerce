
package com.wakacommerce.admin.server.service.persistence.module.provider.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.openadmin.dto.Property;

public interface ProductParentCategoryFieldPersistenceProviderExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType manageParentCategory(Property property, Product product);

}
