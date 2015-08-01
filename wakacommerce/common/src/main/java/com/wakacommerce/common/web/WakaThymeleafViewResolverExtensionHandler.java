package com.wakacommerce.common.web;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public interface WakaThymeleafViewResolverExtensionHandler extends ExtensionHandler {
    
    /**
     * Allows an extension handler to override the view name.
     * @param erh
     * @return
     */
    ExtensionResultStatusType overrideView(ExtensionResultHolder<String> erh, String originalViewName,
            boolean isAjaxRequest);

    /**
     * Allows an extension handler to alter the cache key for the view.
     * @param erh
     * @return
     */
    ExtensionResultStatusType appendCacheKey(ExtensionResultHolder<String> erh, String originalViewName,
            boolean isAjaxRequest);

    /**
     * Allows an extension handler to provide a wrapper for the template.
     * @param erh
     * @return
     */
    ExtensionResultStatusType provideTemplateWrapper(ExtensionResultHolder<String> erh, String originalViewName,
            boolean isAjaxRequest);

}
