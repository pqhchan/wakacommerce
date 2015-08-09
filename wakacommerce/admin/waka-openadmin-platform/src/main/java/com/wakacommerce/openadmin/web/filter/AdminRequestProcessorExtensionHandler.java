
package com.wakacommerce.openadmin.web.filter;

import java.util.Set;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.site.domain.Catalog;
import com.wakacommerce.common.site.domain.Site;

/**
 *
 * @ hui
 */
public interface AdminRequestProcessorExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType retrieveProfiles(Site currentSite, ExtensionResultHolder<Set<Site>> result);

    public ExtensionResultStatusType retrieveCatalogs(Site currentSite, ExtensionResultHolder<Set<Catalog>> result);

}
