
package com.wakacommerce.openadmin.web.service;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

@Component("blFormBuilderExtensionManager")
public class FormBuilderExtensionManager extends ExtensionManager<FormBuilderExtensionHandler> {

    public FormBuilderExtensionManager() {
        super(FormBuilderExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
