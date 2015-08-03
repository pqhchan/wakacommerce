package com.wakacommerce.cms.file.dao;

import org.springframework.web.multipart.MultipartFile;

import com.wakacommerce.cms.file.domain.StaticAssetStorage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

public interface StaticAssetStorageDao {
	
    StaticAssetStorage create();

    StaticAssetStorage readStaticAssetStorageById(Long id);

    StaticAssetStorage readStaticAssetStorageByStaticAssetId(Long id);

    StaticAssetStorage save(StaticAssetStorage assetStorage);

    void delete(StaticAssetStorage assetStorage);

    Blob createBlob(MultipartFile uploadedFile) throws IOException;
    
    Blob createBlob(InputStream uploadedFileInputStream, long fileSize) throws IOException;
    
}
