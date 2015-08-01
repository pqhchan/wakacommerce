
package com.wakacommerce.admin.server.service.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * Extension manager for {@link com.wakacommerce.admin.server.service.AdminCatalogServiceImpl}
 *
 * 
 */
@Service("blAdminCatalogServiceExtensionManager")
public class AdminCatalogServiceExtensionManager extends ExtensionManager<AdminCatalogServiceExtensionHandler> {

    public AdminCatalogServiceExtensionManager() {
        super(AdminCatalogServiceExtensionHandler.class);
    }

}
