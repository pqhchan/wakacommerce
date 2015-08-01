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
    
    /**
     * Overloaded method for {@link #createBlob(MultipartFile)} just in case you do not have the Spring {@link MultipartFile}
     * and are dealing with an already-uploadd file
     * 
     * @param uploadedFileInputStream the input stream of the file the blob should be created from
     * @param fileSize the size of the file which is represented by <b>uploadedFileInputStream</b>, in bytes
     * @return
     * @throws IOException
     */
    Blob createBlob(InputStream uploadedFileInputStream, long fileSize) throws IOException;
    
}
