  
package com.wakacommerce.openadmin.web.controller;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Manager for the {@link com.wakacommerce.openadmin.web.controller.AdminAbstractListGridExtensionHandler}
 * Created by Reginald Cole
 */
@Component("blAdminAbstractListGridExtensionManager")
public class AdminAbstractListGridExtensionManager extends ExtensionManager<AdminAbstractListGridExtensionHandler> {

    public AdminAbstractListGridExtensionManager(){super(AdminAbstractListGridExtensionHandler.class);}
}
