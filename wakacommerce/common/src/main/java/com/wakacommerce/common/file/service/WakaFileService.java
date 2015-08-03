package com.wakacommerce.common.file.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.wakacommerce.common.file.domain.FileWorkArea;

public interface WakaFileService {

    /**
     * 创建一个工作空间
     */
    public FileWorkArea initializeWorkArea();

    /**
     * 关闭工作空间，该方法会删除该空间中的所有东西. 之后对该空间的引用会导致RuntimeError 
     */
    void closeWorkArea(FileWorkArea workArea);

    File getResource(String name);

    File getResource(String name, Long timeout);

    /**
     * Checks for a resource in the temporary directory of the file-system.    Will check for a site-specific file.
     * 
     * @param fullUrl
     * @return
     */
    File getLocalResource(String fullUrl);

    /**
     * Checks for a resource in the temporary directory of the file-system.    Will check for a global (e.g. not site
     * specific file). 
     * 
     * @param fullUrl
     * @return
     */
    File getSharedLocalResource(String fullUrl);

    /**
     * Returns true if the resource is available on the classpath.
     * @param name
     * @return
     */
    boolean checkForResourceOnClassPath(String name);

    /**   
     * Allows assets to be included in the Java classpath.   
     * 
     * This method was designed to support an internal use case and may not have general applicability 
     * beyond that.   
     * For demo sites, many of the product images are shared across the demo sites.   
     * 
     * Rather than copy those images, they are stored in a Jar file and shared by all of the sites.
     * 
     * @param name - fully qualified path to the resource
     * @return
     */
    InputStream getClasspathResource(String name);

    /**
     * Removes the resource from the configured FileProvider
     * 
     * @param name - fully qualified path to the resource
     * @param applicationType - The type of file being accessed
     */
    boolean removeResource(String name);

    /**
     * <p>
     * Takes in a temporary work area and a single File and copies that files to 
     * the configured FileProvider's permanent storage.
     * 
     * <p>
     * Passing in removeFilesFromWorkArea to true allows for more efficient file processing
     * when using a local file system as it performs a move operation instead of a copy.
     * 
     * @param workArea the work area from the given <b>file</b>
     * @param file the file to upload
     * @param removeFilesFromWorkArea whether or not the given <b>file</b> should be removed from <b>workArea</b> when it
     * has been copied
     */
    String addOrUpdateResourceForPath(FileWorkArea workArea, File file, boolean removeFilesFromWorkArea);

    /**
     * <p>
     * Takes in a temporary work area and copies all of the files to the configured FileProvider's permanent storage.
     * 
     * <p>
     * Passing in removeFilesFromWorkArea to true allows for more efficient file processing
     * when using a local file system as it performs a move operation instead of a copy.
     * 
     * @param workArea
     * @param removeFilesFromWorkArea
     */
    List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, boolean removeFilesFromWorkArea);

    /**
     * <p>
     * Takes in a temporary work area and a list of Files and copies them to 
     * the configured FileProvider's permanent storage.
     * 
     * <p>
     * Passing in removeFilesFromWorkArea to true allows for more efficient file processing
     * when using a local file system as it performs a move operation instead of a copy.     
     * 
     * @param workArea the work area for the given <b>files</b>
     * @param files the files to copy to the provider's permanent storage
     * @param removeFilesFromWorkArea whether or not the given <b>files</b> hsould be removed from the given <b>workArea</b>
     * after they are uploaded
     */
    List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea);

}
