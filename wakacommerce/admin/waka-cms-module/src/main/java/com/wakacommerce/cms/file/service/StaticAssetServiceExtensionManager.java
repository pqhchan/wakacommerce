package com.wakacommerce.cms.file.service;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

@Component("blStaticAssetServiceExtensionManager")
public class StaticAssetServiceExtensionManager extends ExtensionManager<StaticAssetServiceExtensionHandler> {

    public StaticAssetServiceExtensionManager() {
        super(StaticAssetServiceExtensionHandler.class);
    }

}
