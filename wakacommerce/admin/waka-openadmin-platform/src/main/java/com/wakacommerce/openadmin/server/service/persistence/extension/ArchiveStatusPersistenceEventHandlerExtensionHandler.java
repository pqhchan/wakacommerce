  
package com.wakacommerce.openadmin.server.service.persistence.extension;

import java.util.concurrent.atomic.AtomicBoolean;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 * Allows for custom conditions to avoid ArchiveStatusPersistenceEventHandler's preFetch functionality
 *
 *Chris Kittrell
 */
public interface ArchiveStatusPersistenceEventHandlerExtensionHandler extends ExtensionHandler {

    /**
     * @param entity
     * @return
     */
    ExtensionResultStatusType isArchivable(Class<?> entity, AtomicBoolean isArchivable);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
}
