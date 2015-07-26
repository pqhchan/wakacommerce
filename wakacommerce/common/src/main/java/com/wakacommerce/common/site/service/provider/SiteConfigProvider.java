
package com.wakacommerce.common.site.service.provider;

import java.util.Map;

import com.wakacommerce.common.site.domain.Site;

/**
 *Jeff Fischer
 */
public interface SiteConfigProvider {

    public void configSite(Site site);
    
    public void init(Map<String, Object> map);

}
