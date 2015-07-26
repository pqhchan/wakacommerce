
package com.wakacommerce.openadmin.web.controller;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Manager for the {@link AdminAbstractControllerExtensionHandler}
 * 
 *Andre Azzolini (apazzolini)
 */
@Component("blAdminAbstractControllerExtensionManager")
public class AdminAbstractControllerExtensionManager extends ExtensionManager<AdminAbstractControllerExtensionHandler> {

    public AdminAbstractControllerExtensionManager() {
        super(AdminAbstractControllerExtensionHandler.class);
    }

}
