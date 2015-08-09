
package com.wakacommerce.common.site.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.site.domain.Site;

/**
 *
 * @ hui
 */
public class AbstractSiteServiceExtensionHandler extends AbstractExtensionHandler implements SiteServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType contributeNonPersitentSiteProperties(Site from, Site to) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
