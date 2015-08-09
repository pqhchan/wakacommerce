
package com.wakacommerce.openadmin.server.service.persistence.module.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.service.persistence.module.BasicPersistenceModule;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface BasicPersistenceModuleExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType rebalanceForUpdate(BasicPersistenceModule basicPersistenceModule,
                                                 PersistencePackage persistencePackage, Serializable instance,
                                                 Map<String, FieldMetadata> mergedProperties, Object primaryKey,
                                                 ExtensionResultHolder<Serializable> resultHolder);

    ExtensionResultStatusType rebalanceForAdd(BasicPersistenceModule basicPersistenceModule,
                                              PersistencePackage persistencePackage, Serializable instance,
                                              Map<String, FieldMetadata> mergedProperties,
                                              ExtensionResultHolder<Serializable> resultHolder);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
}
