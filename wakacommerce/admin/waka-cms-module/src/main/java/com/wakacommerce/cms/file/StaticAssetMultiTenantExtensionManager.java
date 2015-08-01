package com.wakacommerce.cms.file;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.common.file.service.BroadleafStaticAssetExtensionHandler;

@Component("blStaticAssetMultiTenantExtensionManager")
public class StaticAssetMultiTenantExtensionManager extends ExtensionManager<BroadleafStaticAssetExtensionHandler> {

    public StaticAssetMultiTenantExtensionManager() {
        super(BroadleafStaticAssetExtensionHandler.class);
    }

}
