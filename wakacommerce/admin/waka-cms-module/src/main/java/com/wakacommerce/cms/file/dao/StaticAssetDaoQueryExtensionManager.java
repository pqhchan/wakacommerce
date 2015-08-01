package com.wakacommerce.cms.file.dao;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.common.extension.QueryExtensionHandler;

@Component("blStaticAssetDaoQueryExtensionManager")
public class StaticAssetDaoQueryExtensionManager extends ExtensionManager<QueryExtensionHandler> {

    public StaticAssetDaoQueryExtensionManager() {
        super(QueryExtensionHandler.class);
    }

}
