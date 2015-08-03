package com.wakacommerce.common.file.service;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

@Component("blWakaFileServiceExtensionManager")
public class WakaFileServiceExtensionManager extends ExtensionManager<WakaFileServiceExtensionHandler> {

    public WakaFileServiceExtensionManager() {
        super(WakaFileServiceExtensionHandler.class);
    }

}
