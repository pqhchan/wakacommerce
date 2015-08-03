package com.wakacommerce.common.file.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.file.FileServiceException;
import com.wakacommerce.common.file.domain.FileWorkArea;
import com.wakacommerce.common.file.service.type.FileApplicationType;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.web.WakaRequestContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

@Service("blDefaultFileServiceProvider")
public class FileSystemFileServiceProvider implements FileServiceProvider {

    @Value("${asset.server.file.system.path}")
    protected String fileSystemBaseDirectory;

    @Value("${asset.server.max.generated.file.system.directories}")
    protected int maxGeneratedDirectoryDepth;

    @Resource(name = "blWakaFileServiceExtensionManager")
    protected WakaFileServiceExtensionManager extensionManager;

    private static final String DEFAULT_STORAGE_DIRECTORY = System.getProperty("java.io.tmpdir");

    private static final Log LOG = LogFactory.getLog(FileSystemFileServiceProvider.class);

    // Allows for small errors in the configuration (e.g. no trailing slash or whitespace).
    protected String baseDirectory;

    @Override
    public File getResource(String url) {
        return getResource(url, FileApplicationType.ALL);
    }

    @Override
    public File getResource(String url, FileApplicationType applicationType) {
        String fileName = buildResourceName(url);
        String baseDirectory = getBaseDirectory(true);
        ExtensionResultHolder<String> holder = new ExtensionResultHolder<String>();
        if (extensionManager != null){
            ExtensionResultStatusType result = extensionManager.getProxy().processPathForSite(baseDirectory, fileName, holder);
            if (!ExtensionResultStatusType.NOT_HANDLED.equals(result)) {
                return new File(holder.getResult());
            }
        }
        String filePath = FilenameUtils.normalize(getBaseDirectory(false) + File.separator + fileName);
        return new File(filePath);
    }
    
    @Override
    public List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea) {
        List<String> result = new ArrayList<String>();
        for (File srcFile : files) {
            if (!srcFile.getAbsolutePath().startsWith(workArea.getFilePathLocation())) {
                throw new FileServiceException("Attempt to update file " + srcFile.getAbsolutePath() +
                        " that is not in the passed in WorkArea " + workArea.getFilePathLocation());
            }

            String fileName = srcFile.getAbsolutePath().substring(workArea.getFilePathLocation().length());
            
            // before building the resource name, convert the file path to a url-like path
            String url = FilenameUtils.separatorsToUnix(fileName);
            String resourceName = buildResourceName(url);
            String destinationFilePath = FilenameUtils.normalize(getBaseDirectory(false) + File.separator + resourceName);
            File destFile = new File(destinationFilePath);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            
            try {
                if (removeFilesFromWorkArea) {
                    if (destFile.exists()) {
                        FileUtils.deleteQuietly(destFile);
                    }
                    FileUtils.moveFile(srcFile, destFile);
                } else {
                    FileUtils.copyFile(srcFile, destFile);
                }
                result.add(fileName);
            } catch (IOException ioe) {
                throw new FileServiceException("Error copying resource named " + fileName + " from workArea " +
                        workArea.getFilePathLocation() + " to " + resourceName, ioe);
            }
        }
        return result;
    }

    @Override
    public boolean removeResource(String name) {
        String resourceName = buildResourceName(name);
        String filePathToRemove = FilenameUtils.normalize(getBaseDirectory(false) + File.separator + resourceName);
        File fileToRemove = new File(filePathToRemove);
        return fileToRemove.delete();
    }

    protected String buildResourceName(String url) {
        String fileHash = null;
        if (!url.startsWith("/")) {
            fileHash = DigestUtils.md5Hex("/" + url);
        } else {
            fileHash = DigestUtils.md5Hex(url);
        }

        String resourceName = "";
        for (int i = 0; i < maxGeneratedDirectoryDepth; i++) {
            if (i == 4) {
                LOG.warn("Property maxGeneratedDirectoryDepth set to high, ignoring values past 4 - value set to" +
                        maxGeneratedDirectoryDepth);
                break;
            }
            resourceName = FilenameUtils.concat(resourceName, fileHash.substring(i * 2, (i + 1) * 2));
        }

        return FilenameUtils.concat(resourceName, FilenameUtils.getName(url));
    }

    protected String getBaseDirectory(boolean skipSite) {
        if (baseDirectory == null) {
            if (StringUtils.isNotBlank(fileSystemBaseDirectory)) {
                baseDirectory = fileSystemBaseDirectory;
            } else {
                baseDirectory = DEFAULT_STORAGE_DIRECTORY;
            }
        }
        if (!skipSite) {
            return getSiteDirectory(baseDirectory);
        } else {
            return baseDirectory;
        }
    }

    protected String getSiteDirectory(String baseDirectory) {
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {
            Site site = brc.getNonPersistentSite();
            if (site != null) {
                String siteDirectory = "site-" + site.getId();
                String siteHash = DigestUtils.md5Hex(siteDirectory);
                String sitePath = FilenameUtils.concat(siteHash.substring(0, 2), siteDirectory);
                return FilenameUtils.concat(baseDirectory, sitePath);
            }
        }

        return baseDirectory;
    }
}
