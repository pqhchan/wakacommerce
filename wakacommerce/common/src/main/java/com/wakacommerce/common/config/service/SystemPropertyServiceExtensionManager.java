package com.wakacommerce.common.config.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blSystemPropertyServiceExtensionManager")
public class SystemPropertyServiceExtensionManager extends ExtensionManager<SystemPropertyServiceExtensionHandler> {

    public SystemPropertyServiceExtensionManager() {
        super(SystemPropertyServiceExtensionHandler.class);
    }

    /**
     * The first "handler" to "handle" the request wins.
     */
    public boolean continueOnHandled() {
        return false;
    }
}
