package com.wakacommerce.cms.file.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.wakacommerce.cms.file.domain.StaticAsset;
import com.wakacommerce.common.file.service.StaticAssetPathService;

public interface StaticAssetService {

    public StaticAsset findStaticAssetById(Long id);
    
    public List<StaticAsset> readAllStaticAssets();

    public StaticAsset findStaticAssetByFullUrl(String fullUrl);

    public StaticAsset createStaticAssetFromFile(MultipartFile file, Map<String, String> properties);

    public StaticAsset createStaticAsset(InputStream inputStream, String fileName, long fileSize, Map<String, String> properties);

    @Deprecated
    public String getStaticAssetUrlPrefix();

    @Deprecated
    public String getStaticAssetEnvironmentUrlPrefix();

    @Deprecated
    public String getStaticAssetEnvironmentSecureUrlPrefix();

    @Deprecated
    public String convertAssetPath(String assetPath, String contextPath, boolean secureRequest);

    public StaticAsset addStaticAsset(StaticAsset staticAsset);

    public StaticAsset updateStaticAsset(StaticAsset staticAsset);

    public void deleteStaticAsset(StaticAsset staticAsset);

}
