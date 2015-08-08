  
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
 * For internal usage. Allows extending API calls without subclassing the entity.
 *
 * 
 */
public interface BasicPersistenceModuleExtensionHandler extends ExtensionHandler {

    /**
     * Handle reorder change requests from the admin for sortable basic collections
     *
     * @param basicPersistenceModule the persistence module responsible for handling basic collection persistence
     *                               operations
     * @param persistencePackage     the data representing the change
     * @param instance               the persisted entity
     * @param mergedProperties       descriptive data about the entity structure
     * @param primaryKey             the primary key value for the persisted entity
     * @param resultHolder           container for any relevant operation results
     * @return the status of execution for this handler - informs the manager on how to proceed
     */
    ExtensionResultStatusType rebalanceForUpdate(BasicPersistenceModule basicPersistenceModule,
                                                 PersistencePackage persistencePackage, Serializable instance,
                                                 Map<String, FieldMetadata> mergedProperties, Object primaryKey,
                                                 ExtensionResultHolder<Serializable> resultHolder);

    /**
     * Handle additions of new members to a basic collection when the items are sortable
     *
     * @param basicPersistenceModule the persistence module responsible for handling basic collection persistence
     *                               operations
     * @param persistencePackage     the data representing the change
     * @param instance               the persisted entity
     * @param mergedProperties       descriptive data about the entity structure
     * @param resultHolder           container for any relevant operation results
     * @return the status of execution for this handler - informs the manager on how to proceed
     */
    ExtensionResultStatusType rebalanceForAdd(BasicPersistenceModule basicPersistenceModule,
                                              PersistencePackage persistencePackage, Serializable instance,
                                              Map<String, FieldMetadata> mergedProperties,
                                              ExtensionResultHolder<Serializable> resultHolder);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
}
