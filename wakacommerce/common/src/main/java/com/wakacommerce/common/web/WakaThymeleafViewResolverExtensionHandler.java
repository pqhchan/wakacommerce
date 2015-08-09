package com.wakacommerce.common.web;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public interface WakaThymeleafViewResolverExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType overrideView(ExtensionResultHolder<String> erh, String originalViewName,
            boolean isAjaxRequest);

    ExtensionResultStatusType appendCacheKey(ExtensionResultHolder<String> erh, String originalViewName,
            boolean isAjaxRequest);

    ExtensionResultStatusType provideTemplateWrapper(ExtensionResultHolder<String> erh, String originalViewName,
            boolean isAjaxRequest);

}
