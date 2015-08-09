
package com.wakacommerce.openadmin.server.service.persistence.module.provider.extension;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.media.domain.Media;

/**
 *
 * @ hui
 */
public interface MediaFieldPersistenceProviderExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType transformId(Media media, ExtensionResultHolder<Long> resultHolder);

    ExtensionResultStatusType postAdd(Media media);

    ExtensionResultStatusType postUpdate(Media media);

    ExtensionResultStatusType checkDirtyState(Media oldMedia, Media newMedia, ExtensionResultHolder<Boolean> resultHolder);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;

}
