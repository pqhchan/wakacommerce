package com.wakacommerce.common.file.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public interface WakaFileServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType processPathForSite(String prefix, String resourceName, ExtensionResultHolder<String> holder);

}
