package com.wakacommerce.cms.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.wakacommerce.cms.file.domain.StaticAsset;
import com.wakacommerce.cms.file.domain.StaticAssetStorage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Map;

public interface StaticAssetStorageService {

    StaticAssetStorage findStaticAssetStorageById(Long id);

    @Deprecated
    StaticAssetStorage create();

    StaticAssetStorage readStaticAssetStorageByStaticAssetId(Long id);

    StaticAssetStorage save(StaticAssetStorage assetStorage);

    void delete(StaticAssetStorage assetStorage);

    @Deprecated
    Blob createBlob(MultipartFile uploadedFile) throws IOException;

    Map<String, String> getCacheFileModel(String fullUrl, Map<String, String> parameterMap) throws Exception;

    void createStaticAssetStorageFromFile(MultipartFile file, StaticAsset staticAsset) throws IOException;

    void createStaticAssetStorage(InputStream fileInputStream, StaticAsset staticAsset) throws IOException;

}
