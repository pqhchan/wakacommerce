package com.wakacommerce.common.web.deeplink;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.wakacommerce.common.config.RuntimeEnvironmentPropertiesManager;
import com.wakacommerce.common.web.BaseUrlResolver;

public abstract class DeepLinkService<T> {
    
    @Resource(name = "blBaseUrlResolver")
    protected BaseUrlResolver baseUrlResolver;
    
    @Autowired
    protected RuntimeEnvironmentPropertiesManager propMgr;

    public final List<DeepLink> getLinks(T item) {
        return getLinksInternal(item);
    }
    
    protected String getAdminBaseUrl() {
        return baseUrlResolver.getAdminBaseUrl();
    }

    protected abstract List<DeepLink> getLinksInternal(T item);

}
