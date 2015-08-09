
package com.wakacommerce.openadmin.server.service.persistence.module.provider.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blBasicFieldPersistenceProviderExtensionManager")
public class BasicFieldPersistenceProviderExtensionManager extends ExtensionManager<BasicFieldPersistenceProviderExtensionHandler> {

    public BasicFieldPersistenceProviderExtensionManager() {
        super(BasicFieldPersistenceProviderExtensionHandler.class);
    }

}
