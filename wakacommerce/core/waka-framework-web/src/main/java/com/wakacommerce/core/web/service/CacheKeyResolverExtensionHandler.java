
package com.wakacommerce.core.web.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


/**
 *
 * @ hui
 */
public interface CacheKeyResolverExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType updateCacheKey(StringBuilder stringBuilder, boolean hasProducts);
}
