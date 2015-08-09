package com.wakacommerce.common.config.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blSystemPropertyServiceExtensionManager")
public class SystemPropertyServiceExtensionManager extends ExtensionManager<SystemPropertyServiceExtensionHandler> {

    public SystemPropertyServiceExtensionManager() {
        super(SystemPropertyServiceExtensionHandler.class);
    }

    public boolean continueOnHandled() {
        return false;
    }
}
