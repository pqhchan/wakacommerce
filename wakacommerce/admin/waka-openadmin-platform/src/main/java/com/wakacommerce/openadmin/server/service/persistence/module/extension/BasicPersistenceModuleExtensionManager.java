  
package com.wakacommerce.openadmin.server.service.persistence.module.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * For internal usage. Allows extension of entity API calls without subclassing the entity.
 *
 * 
 */
@Service("blBasicPersistenceModuleExtensionManager")
public class BasicPersistenceModuleExtensionManager extends ExtensionManager<BasicPersistenceModuleExtensionHandler> {

    public BasicPersistenceModuleExtensionManager() {
        super(BasicPersistenceModuleExtensionHandler.class);
    }

}
