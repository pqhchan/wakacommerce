
package com.wakacommerce.openadmin.server.security.external;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

@Component("blAdminExternalLoginExtensionManager")
public class AdminExternalLoginUserExtensionManager extends ExtensionManager<AdminExternalLoginExtensionHandler> {

    public AdminExternalLoginUserExtensionManager() {
        super(AdminExternalLoginExtensionHandler.class);
    }

}
