
package com.wakacommerce.core.web.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


public class AbstractCacheKeyResolverExtensionHandler extends AbstractExtensionHandler implements
        CacheKeyResolverExtensionHandler {


    @Override
    public ExtensionResultStatusType updateCacheKey(StringBuilder stringBuilder, boolean hasProducts) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
