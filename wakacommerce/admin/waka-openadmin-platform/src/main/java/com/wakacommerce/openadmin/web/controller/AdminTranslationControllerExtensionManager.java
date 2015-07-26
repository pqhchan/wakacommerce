
package com.wakacommerce.openadmin.web.controller;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Andre Azzolini (apazzolini)
 */
@Component("blAdminTranslationControllerExtensionManager")
public class AdminTranslationControllerExtensionManager extends ExtensionManager<AdminTranslationControllerExtensionHandler> {

    public AdminTranslationControllerExtensionManager() {
        super(AdminTranslationControllerExtensionHandler.class);
    }

}
