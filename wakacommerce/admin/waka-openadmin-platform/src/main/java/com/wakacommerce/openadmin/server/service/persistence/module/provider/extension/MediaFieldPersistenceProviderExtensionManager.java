
package com.wakacommerce.openadmin.server.service.persistence.module.provider.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blMediaFieldPersistenceProviderExtensionManager")
public class MediaFieldPersistenceProviderExtensionManager extends ExtensionManager<MediaFieldPersistenceProviderExtensionHandler> {

    public MediaFieldPersistenceProviderExtensionManager() {
        super(MediaFieldPersistenceProviderExtensionHandler.class);
    }

}
