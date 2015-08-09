  
package com.wakacommerce.openadmin.web.controller;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Component("blAdminAbstractListGridExtensionManager")
public class AdminAbstractListGridExtensionManager extends ExtensionManager<AdminAbstractListGridExtensionHandler> {

    public AdminAbstractListGridExtensionManager(){super(AdminAbstractListGridExtensionHandler.class);}
}
