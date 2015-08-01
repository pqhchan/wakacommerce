
package com.wakacommerce.common.site.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Extension manager that holds the list of {@link SiteServiceExtensionHandler}.
 * 
 *     
 */
@Service("blSiteServiceExtensionManager")
public class SiteServiceExtensionManager extends ExtensionManager<SiteServiceExtensionHandler> {

    public SiteServiceExtensionManager() {
        super(SiteServiceExtensionHandler.class);
    }

}
