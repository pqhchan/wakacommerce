package com.wakacommerce.cms.file.domain;

import java.io.Serializable;

import com.wakacommerce.cms.field.type.StorageType;
import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.openadmin.audit.AdminAuditable;

public interface StaticAsset extends Serializable, MultiTenantCloneable<StaticAsset> {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public String getAltText();

    public void setAltText(String altText);

    public String getTitle();

    public void setTitle(String title);

    public String getFullUrl();

    public void setFullUrl(String fullUrl);

    public Long getFileSize();

    public void setFileSize(Long fileSize);

    public String getMimeType();

    public void setMimeType(String mimeType);

    public String getFileExtension();

    public void setFileExtension(String fileExtension);

    public StorageType getStorageType();

    public void setStorageType(StorageType storageType);

    public AdminAuditable getAuditable();

    public void setAuditable(AdminAuditable auditable);

}
