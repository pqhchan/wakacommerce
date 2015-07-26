
package com.wakacommerce.core.web.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *bpolster
 */
@Service("blCacheKeyResolverExtensionManager")
public class CacheKeyResolverExtensionManager extends ExtensionManager<CacheKeyResolverExtensionHandler> {
    
    public CacheKeyResolverExtensionManager() {
        super(CacheKeyResolverExtensionHandler.class);
    }
}
