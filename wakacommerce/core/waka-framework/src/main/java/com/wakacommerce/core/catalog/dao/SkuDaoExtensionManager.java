
package com.wakacommerce.core.catalog.dao;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Joshua Skorton (jskorton)
 */
@Service("blSkuDaoExtensionManager")
public class SkuDaoExtensionManager extends ExtensionManager<SkuDaoExtensionHandler> {

    public SkuDaoExtensionManager() {
        super(SkuDaoExtensionHandler.class);
    }

}
