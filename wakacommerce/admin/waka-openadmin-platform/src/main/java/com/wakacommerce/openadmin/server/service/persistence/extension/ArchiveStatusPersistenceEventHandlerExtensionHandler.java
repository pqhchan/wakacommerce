
package com.wakacommerce.openadmin.server.service.persistence.extension;

import java.util.concurrent.atomic.AtomicBoolean;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface ArchiveStatusPersistenceEventHandlerExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType isArchivable(Class<?> entity, AtomicBoolean isArchivable);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
}
