package com.wakacommerce.common.web;

/**
 * 负责返回site / admin 的base url
 */
public interface BaseUrlResolver {

    /**
     * 返回为site配置的base url. 默认的实现会返回当前环境下的site.baseurl属性值. 
     * 例如, 开发环境下该方法返回 http://localhost:8080
     * 
     * @return site的base url, 结尾不带"/"
     */
    public String getSiteBaseUrl();

    /**
     * 返回为admin配置的base url. 默认的实现会返回当前环境下的admin.baseurl属性值. 
     * 例如, 开发环境下该方法返回 http://localhost:8080/admin
     * 
     * @return admin的base url, 结尾不带"/"
     */
    public String getAdminBaseUrl();

}
