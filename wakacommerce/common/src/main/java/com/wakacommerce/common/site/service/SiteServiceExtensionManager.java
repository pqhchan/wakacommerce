
package com.wakacommerce.common.site.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blSiteServiceExtensionManager")
public class SiteServiceExtensionManager extends ExtensionManager<SiteServiceExtensionHandler> {

    public SiteServiceExtensionManager() {
        super(SiteServiceExtensionHandler.class);
    }

}
