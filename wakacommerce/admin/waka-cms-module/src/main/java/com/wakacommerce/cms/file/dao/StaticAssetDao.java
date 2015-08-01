package com.wakacommerce.cms.file.dao;

import java.util.List;

import com.wakacommerce.cms.file.domain.StaticAsset;

public interface StaticAssetDao {

    public StaticAsset readStaticAssetById(Long id);
    
    public List<StaticAsset> readAllStaticAssets();

    public void delete(StaticAsset asset);

    public StaticAsset addOrUpdateStaticAsset(StaticAsset asset, boolean clearLevel1Cache);

    public StaticAsset readStaticAssetByFullUrl(String fullUrl);

}
