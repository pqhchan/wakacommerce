package com.wakacommerce.menu.processor;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blMenuProcessorExtensionManager")
public class MenuProcessorExtensionManager  extends ExtensionManager<MenuProcessorExtensionHandler> {

    public MenuProcessorExtensionManager() {
        super(MenuProcessorExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
