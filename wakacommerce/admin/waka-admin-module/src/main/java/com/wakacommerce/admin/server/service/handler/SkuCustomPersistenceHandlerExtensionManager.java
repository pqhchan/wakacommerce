package com.wakacommerce.admin.server.service.handler;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blSkuCustomPersistenceHandlerExtensionManager")
public class SkuCustomPersistenceHandlerExtensionManager extends ExtensionManager<SkuCustomPersistenceHandlerExtensionHandler> {

    public SkuCustomPersistenceHandlerExtensionManager() {
        super(SkuCustomPersistenceHandlerExtensionHandler.class);
    }

}
