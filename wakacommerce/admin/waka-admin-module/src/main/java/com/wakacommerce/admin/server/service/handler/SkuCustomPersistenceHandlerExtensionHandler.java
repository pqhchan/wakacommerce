package com.wakacommerce.admin.server.service.handler;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Sku;

public interface SkuCustomPersistenceHandlerExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType skuUpdated(Sku updated);

    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
}
