
package com.wakacommerce.common.entity.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Extension manager that holds the list of {@link EntityInformationServiceExtensionHandler}.
 * 
 */
@Service("blEntityInformationServiceExtensionManager")
public class EntityInformationServiceExtensionManager extends ExtensionManager<EntityInformationServiceExtensionHandler> {

    public EntityInformationServiceExtensionManager() {
        super(EntityInformationServiceExtensionHandler.class);
    }

}
