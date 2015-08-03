package com.wakacommerce.cms.file;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.common.file.service.WakaStaticAssetExtensionHandler;

@Component("blStaticAssetMultiTenantExtensionManager")
public class StaticAssetMultiTenantExtensionManager extends ExtensionManager<WakaStaticAssetExtensionHandler> {

    public StaticAssetMultiTenantExtensionManager() {
        super(WakaStaticAssetExtensionHandler.class);
    }

}
