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

    /**
     * <p>
     * 根据上传的文件创建StaticAsset对象. 依赖于具体的实现，该资源既可能存放在数据库中，也可能
     * 存在文件系统中. 默认的实现 {@link StaticAssetServiceImpl} 会根据系统属性<code>asset.use.filesystem.storage</code>
     * 来判断
     * 
     * <p>
     * properties参数可以让实现在上传文件的同时更新Asset的其它一些属性，比如指定URL
     * 
     * <p>
     * 要真正地创建一个该asset表示的物理文件可以参考
     * {@link StaticAssetStorageService#createStaticAssetStorageFromFile(MultipartFile, StaticAsset)}
     * 
     * @see StaticAssetServiceImpl
     * 
     * @param file
     * @param properties
     */
    public StaticAsset createStaticAssetFromFile(MultipartFile file, Map<String, String> properties);
    
    /**
     * <p>
     * 和 {@link #createStaticAssetFromFile(MultipartFile, Map)} 类似、
     * 
     * @param inputStream
     * @param fileName
     * @param fileSize
     * @param properties
     * 
     */
    public StaticAsset createStaticAsset(InputStream inputStream, String fileName, long fileSize, Map<String, String> properties);

    /**
     * @see StaticAssetPathService#getStaticAssetUrlPrefix()
     * @deprecated since 3.1.0. 
     */
    @Deprecated
    public String getStaticAssetUrlPrefix();

    /**
     * @see StaticAssetPathService#getStaticAssetEnvironmentUrlPrefix()
     * @deprecated since 3.1.0. 
     */
    @Deprecated
    public String getStaticAssetEnvironmentUrlPrefix();

    /**
     * @see StaticAssetPathService#getStaticAssetEnvironmentSecureUrlPrefix()
     * @deprecated since 3.1.0. 
     */
    @Deprecated
    public String getStaticAssetEnvironmentSecureUrlPrefix();

    /**
     * @see StaticAssetPathService#convertAssetPath(String, String, boolean)
     * @deprecated since 3.1.0. 
     */
    @Deprecated
    public String convertAssetPath(String assetPath, String contextPath, boolean secureRequest);

    public StaticAsset addStaticAsset(StaticAsset staticAsset);

    public StaticAsset updateStaticAsset(StaticAsset staticAsset);

    public void deleteStaticAsset(StaticAsset staticAsset);

}
