package com.wakacommerce.common.file.service;

public interface StaticAssetPathService {

    /**
     * 将content中包含的"staticAssetUrlPrefix"替换成属性"staticAssetEnvironmentUrlPrefix"或
     *
     * @param content       
     * @param secureRequest
     */
    public String convertAllAssetPathsInContent(String content, boolean secureRequest);

    /**
     * 将assetPath中包含的"staticAssetUrlPrefix"替换成属性"staticAssetEnvironmentUrlPrefix"或
     * 
     * @param assetPath
     * @param contextPath
     * @param secureRequest
     * 
     * @see StaticAssetService#getStaticAssetUrlPrefix()
     * @see StaticAssetService#getStaticAssetEnvironmentUrlPrefix()
     */
    public String convertAssetPath(String assetPath, String contextPath, boolean secureRequest);

    public String getStaticAssetUrlPrefix();

    public void setStaticAssetUrlPrefix(String prefix);

    public String getStaticAssetEnvironmentUrlPrefix();

    public void setStaticAssetEnvironmentUrlPrefix(String prefix);

    public String getStaticAssetEnvironmentSecureUrlPrefix();

}
