package com.wakacommerce.cms.structure.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blStructuredContentServiceExtensionManager")
public class StructuredContentServiceExtensionManager extends ExtensionManager<StructuredContentServiceExtensionHandler> {

    public StructuredContentServiceExtensionManager() {
        super(StructuredContentServiceExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }
    
}
