package com.wakacommerce.cms.web.processor;

import com.wakacommerce.common.extension.ExtensionManager;

public class ContentProcessorExtensionManager extends ExtensionManager<ContentProcessorExtensionHandler> {

    public ContentProcessorExtensionManager() {
        super(ContentProcessorExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
