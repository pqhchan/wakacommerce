
package com.wakacommerce.openadmin.server.service.persistence.module.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blBasicPersistenceModuleExtensionManager")
public class BasicPersistenceModuleExtensionManager extends ExtensionManager<BasicPersistenceModuleExtensionHandler> {

    public BasicPersistenceModuleExtensionManager() {
        super(BasicPersistenceModuleExtensionHandler.class);
    }

}
