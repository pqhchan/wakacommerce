
package com.wakacommerce.core.web.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blCacheKeyResolverExtensionManager")
public class CacheKeyResolverExtensionManager extends ExtensionManager<CacheKeyResolverExtensionHandler> {
    
    public CacheKeyResolverExtensionManager() {
        super(CacheKeyResolverExtensionHandler.class);
    }
}
