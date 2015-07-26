

package com.wakacommerce.common.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.wakacommerce.common.config.RuntimeEnvironmentPropertiesManager;


public class BaseUrlResolverImpl implements BaseUrlResolver {

    @Autowired
    protected RuntimeEnvironmentPropertiesManager propMgr;
    
    @Override
    public String getSiteBaseUrl() {
        String baseUrl = propMgr.getProperty("site.baseurl");
        if (baseUrl.charAt(baseUrl.length() - 1) == '/') {
            return baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl;
    }

    @Override
    public String getAdminBaseUrl() {
        String baseUrl = propMgr.getProperty("admin.baseurl");
        if (baseUrl.charAt(baseUrl.length() - 1) == '/') {
            return baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl;
    }
    
}
