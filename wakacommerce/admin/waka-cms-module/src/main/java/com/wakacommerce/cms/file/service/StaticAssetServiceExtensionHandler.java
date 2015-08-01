package com.wakacommerce.cms.file.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public interface StaticAssetServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType fileExists(String fileName, ExtensionResultHolder holder);

}
