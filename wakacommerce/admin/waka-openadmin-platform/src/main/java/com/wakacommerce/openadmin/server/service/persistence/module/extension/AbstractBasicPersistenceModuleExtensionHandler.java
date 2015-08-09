
package com.wakacommerce.openadmin.server.service.persistence.module.extension;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
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
public class AbstractBasicPersistenceModuleExtensionHandler extends AbstractExtensionHandler implements BasicPersistenceModuleExtensionHandler {

    @Override
    public ExtensionResultStatusType rebalanceForUpdate(BasicPersistenceModule basicPersistenceModule,
                                                        PersistencePackage persistencePackage, Serializable instance,
                                                        Map<String, FieldMetadata> mergedProperties,
                                                        Object primaryKey, ExtensionResultHolder<Serializable> resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType rebalanceForAdd(BasicPersistenceModule basicPersistenceModule,
                                                     PersistencePackage persistencePackage, Serializable instance,
                                                     Map<String, FieldMetadata> mergedProperties,
                                                     ExtensionResultHolder<Serializable> resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
