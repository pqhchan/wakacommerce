
package com.wakacommerce.core.web.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


/**
 *bpolster
 */
public interface CacheKeyResolverExtensionHandler extends ExtensionHandler {

    /**
     * The passed in StringBuilder represents the current state of the cache key prior
     * to running any extension handlers.
     * 
     * Any implementations of this processor can read modify the passed in stringBuilder as
     * needed.    
     * 
     * @param stringBuilder
     * @param hasProducts
     * @return
     */
    public ExtensionResultStatusType updateCacheKey(StringBuilder stringBuilder, boolean hasProducts);
}
