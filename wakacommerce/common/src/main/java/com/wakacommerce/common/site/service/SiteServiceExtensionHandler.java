
package com.wakacommerce.common.site.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.site.domain.SiteImpl;

/**
 * <p>
 * ExtensionHandler for methods within {@link SiteServiceImpl}
 * 
 * <p>
 * Rather than implementing this interface directly you should extend your implementation from
 * {@link AbstractSiteServiceExtensionHandler}.
 * 
 *Phillip Verheyden (phillipuniverse)
 * @see {@link AbstractSiteServiceExtensionHandler}
 */
public interface SiteServiceExtensionHandler extends ExtensionHandler {

    /**
     * Invoked via {@link SiteServiceImpl#getNonPersistentSite(Site)} after the initial framework clone. If more properties
     * are dynamically weaved into {@link SiteImpl} then they should be cloned here.
     * 
     * @param from the {@link Site} being copied from, usually just looked up from the database
     * @param to the 
     * @see {@link SiteServiceImpl#getNonPersistentSite(Site)}
     */
    public ExtensionResultStatusType contributeNonPersitentSiteProperties(Site from, Site to);

}
