
package com.wakacommerce.common.site.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.site.domain.SiteImpl;

/**
 *
 * @ hui
 */
public interface SiteServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType contributeNonPersitentSiteProperties(Site from, Site to);

}
