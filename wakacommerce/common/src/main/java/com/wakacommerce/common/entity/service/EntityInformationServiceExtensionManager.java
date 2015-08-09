
package com.wakacommerce.common.entity.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blEntityInformationServiceExtensionManager")
public class EntityInformationServiceExtensionManager extends ExtensionManager<EntityInformationServiceExtensionHandler> {

    public EntityInformationServiceExtensionManager() {
        super(EntityInformationServiceExtensionHandler.class);
    }

}
