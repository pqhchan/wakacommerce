  
package com.wakacommerce.openadmin.server.service.persistence.module.provider.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;

import java.io.Serializable;

/**
 * For internal usage. Allows extending API calls without subclassing the entity.
 *
 * 
 */
public interface BasicFieldPersistenceProviderExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType transformId(ExtractValueRequest request, Property property, ExtensionResultHolder<Serializable> resultHolder);

    ExtensionResultStatusType transformForeignKey(ExtractValueRequest request, Property property, ExtensionResultHolder<Serializable> resultHolder);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;

}
