
package com.wakacommerce.common.web.deeplink;


import org.springframework.beans.factory.annotation.Autowired;

import com.wakacommerce.common.config.RuntimeEnvironmentPropertiesManager;
import com.wakacommerce.common.web.BaseUrlResolver;

import java.util.List;

import javax.annotation.Resource;

/**
 * This abstract class should be extended by services that provide deep links for specific entities.
 * 
 *Andre Azzolini (apazzolini)
 */
public abstract class DeepLinkService<T> {
    
    @Resource(name = "blBaseUrlResolver")
    protected BaseUrlResolver baseUrlResolver;
    
    @Autowired
    protected RuntimeEnvironmentPropertiesManager propMgr;

    /**
     * Returns a list of {@link DeepLink} objects that represent the location of 1 or more admin elements
     * 
     * @param item
     * @return the list of DeepLinks
     */
    public final List<DeepLink> getLinks(T item) {
        return getLinksInternal(item);
    }
    
    protected String getAdminBaseUrl() {
        return baseUrlResolver.getAdminBaseUrl();
    }

    protected abstract List<DeepLink> getLinksInternal(T item);

}
