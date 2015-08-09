
package com.wakacommerce.openadmin.server.service.persistence.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blArchiveStatusPersistenceEventHandlerExtensionManager")
public class ArchiveStatusPersistenceEventHandlerExtensionManager extends ExtensionManager<ArchiveStatusPersistenceEventHandlerExtensionHandler> {

    public ArchiveStatusPersistenceEventHandlerExtensionManager() {
        super(ArchiveStatusPersistenceEventHandlerExtensionHandler.class);
    }

}
