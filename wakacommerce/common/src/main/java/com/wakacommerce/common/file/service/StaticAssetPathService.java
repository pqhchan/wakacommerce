package com.wakacommerce.common.file.service;

public interface StaticAssetPathService {

    public String convertAllAssetPathsInContent(String content, boolean secureRequest);

    public String convertAssetPath(String assetPath, String contextPath, boolean secureRequest);

    public String getStaticAssetUrlPrefix();

    public void setStaticAssetUrlPrefix(String prefix);

    public String getStaticAssetEnvironmentUrlPrefix();

    public void setStaticAssetEnvironmentUrlPrefix(String prefix);

    public String getStaticAssetEnvironmentSecureUrlPrefix();

}
