package com.wakacommerce.cms.file.domain;

import java.sql.Blob;

public interface StaticAssetStorage {

    Long getId();

    void setId(Long id);

    Blob getFileData();

    void setFileData(Blob fileData);

    public Long getStaticAssetId();

    public void setStaticAssetId(Long staticAssetId);

}
