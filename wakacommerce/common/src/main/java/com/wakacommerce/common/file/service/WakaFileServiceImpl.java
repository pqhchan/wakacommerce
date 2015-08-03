package com.wakacommerce.common.file.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.file.domain.FileWorkArea;
import com.wakacommerce.common.file.exception.FileServiceException;
import com.wakacommerce.common.web.WakaRequestContext;

@Service("blFileService")
public class WakaFileServiceImpl implements WakaFileService {
    
    private static final Log LOG = LogFactory.getLog(WakaFileServiceImpl.class);

    @Resource(name = "blFileServiceProviders")
    protected List<FileServiceProvider> fileServiceProviders = new ArrayList<FileServiceProvider>();
    
    @Resource(name = "blDefaultFileServiceProvider")
    protected FileServiceProvider defaultFileServiceProvider;

    private static final String DEFAULT_STORAGE_DIRECTORY = System.getProperty("java.io.tmpdir");
    
    @Value("${file.service.temp.file.base.directory}")
    protected String tempFileSystemBaseDirectory;    
    
    @Value("${asset.server.max.generated.file.system.directories}")
    protected int maxGeneratedDirectoryDepth = 2;
    
    @Value("${asset.server.file.classpath.directory}")
    protected String fileServiceClasspathDirectory;

    @Resource(name = "blWakaFileServiceExtensionManager")
    protected WakaFileServiceExtensionManager extensionManager;

    @Override
    public FileWorkArea initializeWorkArea() {
        String baseDirectory = getBaseDirectory(false);
        String tempDirectory = getTempDirectory(baseDirectory);
        FileWorkArea fw = new FileWorkArea();
        fw.setFilePathLocation(tempDirectory);
        
        File tmpDir = new File(tempDirectory);

        if (!tmpDir.exists()) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Creating temp directory named " + tempDirectory);
            }
            if (!tmpDir.mkdirs()) {
                throw new FileServiceException("Unable to create temporary working directory for " + tempDirectory);
            }
        }

        return fw;
    }

    @Override
    public void closeWorkArea(FileWorkArea fwArea) {
        File tempDirectory = new File(fwArea.getFilePathLocation());
        try {
            if (tempDirectory.exists()) {
                FileUtils.deleteDirectory(tempDirectory);
            }

            for (int i = 1; i < maxGeneratedDirectoryDepth; i++) {
                tempDirectory = tempDirectory.getParentFile();
                if (tempDirectory.list().length == 0 && tempDirectory.exists()) {
                    FileUtils.deleteDirectory(tempDirectory);
                }
            }

        } catch (IOException ioe) {
            throw new FileServiceException("Unable to delete temporary working directory for " + tempDirectory, ioe);
        }
    }

    @Override
    public File getResource(String name) {
        return selectFileServiceProvider().getResource(name);
    }

    protected File getLocalResource(String resourceName, boolean skipSite) {
    	//TODO 实现逻辑貌似有问题，回头修正
    	if (skipSite) {
            String baseDirectory = getBaseDirectory(skipSite);
            // convert the separators to the system this is currently run on
            String systemResourcePath = FilenameUtils.separatorsToSystem(resourceName);
            String filePath = FilenameUtils.normalize(baseDirectory + File.separator + systemResourcePath);
            return new File(filePath);
        } else {
           String baseDirectory = getBaseDirectory(true);
           ExtensionResultHolder<String> holder = new ExtensionResultHolder<String>();
           if (extensionManager != null) {
               ExtensionResultStatusType result = extensionManager.getProxy().processPathForSite(baseDirectory, resourceName, holder);
               if (!ExtensionResultStatusType.NOT_HANDLED.equals(result)) {
                   return new File(holder.getResult());
               }
           }
           return getLocalResource(resourceName, true);
        }
    }

    @Override
    public File getLocalResource(String resourceName) {
        return getLocalResource(resourceName, false);
    }

    @Override
    public File getSharedLocalResource(String resourceName) {
        return getLocalResource(resourceName, true);
    }

    @Override
    public File getResource(String name, Long localTimeout) {
        File returnFile = getLocalResource(name);
        if (returnFile != null && returnFile.exists()) {
            if (localTimeout != null) {
                long lastModified = returnFile.lastModified();
                long now = System.currentTimeMillis();
                if ((now - lastModified) <= localTimeout.longValue()) {
                    return returnFile;
                }
            } else {
                return returnFile;
            }
        }

        return getResource(name);
    }

    @Override
    public boolean checkForResourceOnClassPath(String name) {
        ClassPathResource resource = lookupResourceOnClassPath(name);
        return (resource != null && resource.exists());
    }

    protected ClassPathResource lookupResourceOnClassPath(String name) {
        if (fileServiceClasspathDirectory != null && !"".equals(fileServiceClasspathDirectory)) {
            try {
                String resourceName = FilenameUtils.separatorsToUnix(FilenameUtils.normalize(fileServiceClasspathDirectory + '/' + name));
                ClassPathResource resource = new ClassPathResource(resourceName);
                if (resource.exists()) {
                    return resource;
                }
            } catch (Exception e) {
                LOG.error("Error getting resource from classpath", e);
            }
        }
        return null;
    }

    @Override
    public InputStream getClasspathResource(String name) {
        try {
            ClassPathResource resource = lookupResourceOnClassPath(name);
            if (resource != null && resource.exists()) {
                InputStream assetFile = resource.getInputStream();
                BufferedInputStream bufferedStream = new BufferedInputStream(assetFile);

                // Wrapping the buffered input stream with a globally shared stream allows us to 
                // vary the way the file names are generated on the file system.    
                // This benefits us (mainly in our demo site but their could be other uses) when we
                // have assets that are shared across sites that we also need to resize. 
                GloballySharedInputStream globallySharedStream = new GloballySharedInputStream(bufferedStream);
                globallySharedStream.mark(0);
                return globallySharedStream;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.error("Error getting resource from classpath", e);
        }
        return null;
    }

    @Override
    public boolean removeResource(String resourceName) {
        return selectFileServiceProvider().removeResource(resourceName);
    }

    @Override
    public String addOrUpdateResourceForPath(FileWorkArea workArea, File file, boolean removeFilesFromWorkArea) {
        List<File> files = new ArrayList<File>();
        files.add(file);
        return addOrUpdateResourcesForPaths(workArea, files, removeFilesFromWorkArea).get(0);
    }

    @Override
    public List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, boolean removeFilesFromWorkArea) {
        File folder = new File(workArea.getFilePathLocation());
        List<File> fileList = new ArrayList<File>();
        buildFileList(folder, fileList);
        return addOrUpdateResourcesForPaths(workArea, fileList, removeFilesFromWorkArea);
    }
    
    @Override
    public List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea) {
        checkFiles(workArea, files);
        return selectFileServiceProvider().addOrUpdateResourcesForPaths(workArea, files, removeFilesFromWorkArea);
    }

    /**
     * Returns the FileServiceProvider that can handle the passed in application type.
     * 
     * By default, this method returns the component configured at blFileServiceProvider
     */
    protected FileServiceProvider selectFileServiceProvider() {
        return defaultFileServiceProvider;
    }

    protected void checkFiles(FileWorkArea workArea, List<File> fileList) {
        for (File file : fileList) {
            String fileName = file.getAbsolutePath();
            if (!fileName.startsWith(workArea.getFilePathLocation())) {
                throw new FileServiceException("File operation attempted on file that is not in provided work area. "
                        + fileName + ".  Work area = " + workArea.getFilePathLocation());
            }
            if (!file.exists()) {
                throw new FileServiceException("Add or Update Resource called with filename that does not exist.  " + fileName);
            }
        }
    }

    protected String getBaseDirectory(boolean skipSite) {
        String path = "";
        if (StringUtils.isBlank(tempFileSystemBaseDirectory)) {
            path = DEFAULT_STORAGE_DIRECTORY;
        } else {
            path = tempFileSystemBaseDirectory;
        }

        if (!skipSite) {
            // Create site specific directory if Multi-site (all site files will be located in the same directory)
            WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
            if (brc != null && brc.getNonPersistentSite() != null) {
                String siteDirectory = "site-" + brc.getNonPersistentSite().getId();
                String siteHash = DigestUtils.md5Hex(siteDirectory);
                path = FilenameUtils.concat(path, siteHash.substring(0, 2));
                path = FilenameUtils.concat(path, siteDirectory);
            }
        }

        return path;
    }

    protected String getTempDirectory(String baseDirectory) {
        assert baseDirectory != null;

        Random random = new Random();
        // This code is used to ensure that we don't have thousands of sub-directories in a single parent directory.
        for (int i = 0; i < maxGeneratedDirectoryDepth; i++) {
            if (i == 4) {
                LOG.warn("Property asset.server.max.generated.file.system.directories set to high, currently set to " +
                        maxGeneratedDirectoryDepth);
                break;
            }
            int num = random.nextInt(256);
            baseDirectory = FilenameUtils.concat(baseDirectory, Integer.toHexString(num));
        }
        return baseDirectory;
    }

    /**
     * Adds the file to the passed in Collection.
     * If the file is a directory, adds its children recursively.   Otherwise, just adds the file to the list.    
     * @param file
     * @param fileList
     */
    protected void buildFileList(File file, Collection<File> fileList) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (child.isDirectory()) {
                        buildFileList(child, fileList);
                    } else {
                        fileList.add(child);
                    }
                }
            }
        } else {
            fileList.add(file);
        }
    }

    public String getTempFileSystemBaseDirectory() {
        return tempFileSystemBaseDirectory;
    }
    
    public void setTempFileSystemBaseDirectory(String tempFileSystemBaseDirectory) {
        this.tempFileSystemBaseDirectory = tempFileSystemBaseDirectory;
    }

    public List<FileServiceProvider> getFileServiceProviders() {
        return fileServiceProviders;
    }

    public void setFileServiceProviders(List<FileServiceProvider> fileServiceProviders) {
        this.fileServiceProviders = fileServiceProviders;
    }

    public int getMaxGeneratedDirectoryDepth() {
        return maxGeneratedDirectoryDepth;
    }

    public void setMaxGeneratedDirectoryDepth(int maxGeneratedDirectoryDepth) {
        this.maxGeneratedDirectoryDepth = maxGeneratedDirectoryDepth;
    }

    public FileServiceProvider getDefaultFileServiceProvider() {
        return defaultFileServiceProvider;
    }

    public void setDefaultFileServiceProvider(FileServiceProvider defaultFileServiceProvider) {
        this.defaultFileServiceProvider = defaultFileServiceProvider;
    }

}
