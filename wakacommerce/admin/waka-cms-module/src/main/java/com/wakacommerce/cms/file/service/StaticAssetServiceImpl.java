package com.wakacommerce.cms.file.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wakacommerce.cms.field.type.StorageType;
import com.wakacommerce.cms.file.dao.StaticAssetDao;
import com.wakacommerce.cms.file.domain.ImageStaticAsset;
import com.wakacommerce.cms.file.domain.ImageStaticAssetImpl;
import com.wakacommerce.cms.file.domain.StaticAsset;
import com.wakacommerce.cms.file.domain.StaticAssetImpl;
import com.wakacommerce.common.file.service.StaticAssetPathService;
import com.wakacommerce.common.util.TransactionUtils;
import com.wakacommerce.openadmin.server.service.artifact.image.ImageArtifactProcessor;
import com.wakacommerce.openadmin.server.service.artifact.image.ImageMetadata;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.detector.ExtensionMimeDetector;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

@Service("blStaticAssetService")
public class StaticAssetServiceImpl implements StaticAssetService {

    private static final Log LOG = LogFactory.getLog(StaticAssetServiceImpl.class);

    @Resource(name = "blImageArtifactProcessor")
    protected ImageArtifactProcessor imageArtifactProcessor;

    @Value("${asset.use.filesystem.storage}")
    protected boolean storeAssetsOnFileSystem = false;

    @Resource(name="blStaticAssetDao")
    protected StaticAssetDao staticAssetDao;

    @Resource(name="blStaticAssetStorageService")
    protected StaticAssetStorageService staticAssetStorageService;
    
    @Resource(name = "blStaticAssetPathService")
    protected StaticAssetPathService staticAssetPathService;

    private final Random random = new Random();
    private final String FILE_NAME_CHARS = "0123456789abcdef";

    @Override
    public StaticAsset findStaticAssetById(Long id) {
        return staticAssetDao.readStaticAssetById(id);
    }
    
    @Override
    public List<StaticAsset> readAllStaticAssets() {
        return staticAssetDao.readAllStaticAssets();
    }

    static {
        MimeUtil.registerMimeDetector(ExtensionMimeDetector.class.getName());
        MimeUtil.registerMimeDetector(MagicMimeMimeDetector.class.getName());
    }

    protected String getFileExtension(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos > 0) {
            return fileName.substring(pos + 1, fileName.length()).toLowerCase();
        } else {
            LOG.warn("No extension provided for asset : " + fileName);
            return null;
        }
    }

    /**
     * Generates a filename as a set of Hex digits.
     * @param size
     * @return
     */
    protected String generateFileName(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int pos = random.nextInt(FILE_NAME_CHARS.length());
            sb = sb.append(FILE_NAME_CHARS.charAt(pos));
        }
        return sb.toString();
    }

    /**
     * Will assemble the url from the passed in properties as 
     *     /{entityType}/{fileName}
     *     /product/7001-ab12
     * 
     * If the properties above are not set, it will generate the fileName randomly.
     *     
     * @param url
     * @param asset
     * @param assetProperties
     * @return
     */
    protected String buildAssetURL(Map<String, String> assetProperties, String originalFilename) {
        StringBuilder path = new StringBuilder("/");
        
        String entityType = assetProperties.get("entityType");
        String entityId = assetProperties.get("entityId");
        String fileName = assetProperties.get("fileName");
        
        if (entityType != null && !"null".equals(entityType)) {
            path = path.append(entityType).append("/");
        } else {
            LOG.warn("The given entityType to build the asset URL was null for file " + originalFilename + " and entityId " + entityId + ", investigate probably cause");
        }

        if (entityId != null && !"null".equals(entityId)) {
            path = path.append(entityId).append("/");
        } else {
            LOG.warn("The given entityId to build the asset URL was null for file " + originalFilename + " and entityType " + entityType + ", investigate probably cause");
        }

        if (fileName != null) {
            int pos = fileName.indexOf(":");
            if (pos > 0) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Removing protocol from URL name" + fileName);
                }
                fileName = fileName.substring(pos + 1);
            }
        } else {
            fileName = originalFilename;
        }

        return path.append(fileName).toString();
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public StaticAsset createStaticAssetFromFile(MultipartFile file, Map<String, String> properties) {
        try {
            return createStaticAsset(file.getInputStream(), file.getOriginalFilename(), file.getSize(), properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public StaticAsset createStaticAsset(InputStream inputStream, String fileName, long fileSize, Map<String, String> properties) {
        if (properties == null) {
            properties = new HashMap<String, String>();
        }

        String fullUrl = buildAssetURL(properties, fileName);
        StaticAsset newAsset = staticAssetDao.readStaticAssetByFullUrl(fullUrl);
        int count = 0;
        while (newAsset != null) {
            count++;
            
            //try the new format first, then the old
            newAsset = staticAssetDao.readStaticAssetByFullUrl(getCountUrl(fullUrl, count, false));
            if (newAsset == null) {
                newAsset = staticAssetDao.readStaticAssetByFullUrl(getCountUrl(fullUrl, count, true));
            }
        }

        if (count > 0) {
            fullUrl = getCountUrl(fullUrl, count, false);
        }

        try {
            ImageMetadata metadata = imageArtifactProcessor.getImageMetadata(inputStream);
            newAsset = new ImageStaticAssetImpl();
            ((ImageStaticAsset) newAsset).setWidth(metadata.getWidth());
            ((ImageStaticAsset) newAsset).setHeight(metadata.getHeight());
        } catch (Exception e) {
            //must not be an image stream
            newAsset = new StaticAssetImpl();
        }
        if (storeAssetsOnFileSystem) {
            newAsset.setStorageType(StorageType.FILESYSTEM);
        } else {
            newAsset.setStorageType(StorageType.DATABASE);
        }

        newAsset.setName(fileName);
        getMimeType(inputStream, fileName, newAsset);
        newAsset.setFileExtension(getFileExtension(fileName));
        newAsset.setFileSize(fileSize);
        newAsset.setFullUrl(fullUrl);

        return staticAssetDao.addOrUpdateStaticAsset(newAsset, false);
    }
    
    /**
     * Gets the count URL based on the original fullUrl. If requested in legacy format this will return URLs like:
     * 
     *  /path/to/image.jpg-1
     *  /path/to/image.jpg-2
     *  
     * Whereas if this is in non-lagacy format (<b>legacy</b> == false):
     * 
     *  /path/to/image-1.jpg
     *  /path/to/image-2.jpg
     *  
     * Used to deal with duplicate URLs of uploaded assets
     *  
     */
    protected String getCountUrl(String fullUrl, int count, boolean legacyFormat) {
        String countUrl = fullUrl + '-' + count;
        int dotIndex = fullUrl.lastIndexOf('.');
        if (dotIndex != -1 && !legacyFormat) {
            countUrl = fullUrl.substring(0, dotIndex) + '-' + count + '.' + fullUrl.substring(dotIndex + 1);
        }
        
        return countUrl;
    }

    protected void getMimeType(InputStream inputStream, String fileName, StaticAsset newAsset) {
        Collection mimeTypes = MimeUtil.getMimeTypes(fileName);
        if (!mimeTypes.isEmpty()) {
            MimeType mimeType = (MimeType) mimeTypes.iterator().next();
            newAsset.setMimeType(mimeType.toString());
        } else {
            mimeTypes = MimeUtil.getMimeTypes(inputStream);
            if (!mimeTypes.isEmpty()) {
                MimeType mimeType = (MimeType) mimeTypes.iterator().next();
                newAsset.setMimeType(mimeType.toString());
            }
        }
    }

    @Override
    public StaticAsset findStaticAssetByFullUrl(String fullUrl) {
        try {
            fullUrl = URLDecoder.decode(fullUrl, "UTF-8");
            //strip out the jsessionid if it's there
            fullUrl = fullUrl.replaceAll("(?i);jsessionid.*?=.*?(?=\\?|$)", "");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported encoding to decode fullUrl", e);
        }
        return staticAssetDao.readStaticAssetByFullUrl(fullUrl);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public StaticAsset addStaticAsset(StaticAsset staticAsset) {
        StaticAsset newAsset = staticAssetDao.addOrUpdateStaticAsset(staticAsset, true);
        return newAsset;
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public StaticAsset updateStaticAsset(StaticAsset staticAsset) {
        return staticAssetDao.addOrUpdateStaticAsset(staticAsset, true);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public void deleteStaticAsset(StaticAsset staticAsset) {
        staticAssetDao.delete(staticAsset);
    }

    @Override
    public String getStaticAssetUrlPrefix() {
        return staticAssetPathService.getStaticAssetUrlPrefix();
    }

    @Override
    public String getStaticAssetEnvironmentUrlPrefix() {
        return staticAssetPathService.getStaticAssetEnvironmentUrlPrefix();
    }

    @Override
    public String getStaticAssetEnvironmentSecureUrlPrefix() {
        return staticAssetPathService.getStaticAssetEnvironmentSecureUrlPrefix();
    }

    @Override
    public String convertAssetPath(String assetPath, String contextPath, boolean secureRequest) {
        return staticAssetPathService.convertAssetPath(assetPath, contextPath, secureRequest);
    }

}
