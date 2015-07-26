
package com.wakacommerce.openadmin.web.filter;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Jeff Fischer
 */
@Component("blAdminRequestProcessorExtensionManager")
public class AdminRequestProcessorExtensionManager extends ExtensionManager<AdminRequestProcessorExtensionHandler> {

    public AdminRequestProcessorExtensionManager() {
        super(AdminRequestProcessorExtensionHandler.class);
    }

}
