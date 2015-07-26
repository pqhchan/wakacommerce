
package com.wakacommerce.common.file.service;

import java.io.File;
import java.util.List;

import com.wakacommerce.common.file.domain.FileWorkArea;
import com.wakacommerce.common.file.service.type.FileApplicationType;

/**
 * Interface to be implemented by a FileProvider.   This could be a local FileProvider or a remote service like Amazon S3.
 * 
 *bpolster
 *
 */
public interface FileServiceProvider {
    
    /**
     * Returns a File representing the passed in url. All separators in the given <b>url</b> should be in URL-separator form
     * meaning '/' rather than '\' (like on Windows).
     * 
     * @param name - fully qualified path to the resource
     * @return
     */
    File getResource(String url);

    /**
     * Returns a File representing the passed in name and application type.   Providers may choose to 
     * cache certain FileApplicationType(s) locally rather than retrieve them from a remote source.   
     *  
     * @param url - the URL-representation of the resource. This means that paths should always have / separators rather than
     * system-specific values
     * @param fileApplicationType applicationType
     * @return a File to the 
     */
    File getResource(String url, FileApplicationType fileApplicationType);

    /**
     * Takes in a work area and application type and moves all of the files to the configured FileProvider.
     * 
     * @param workArea
     * @param files the files that should be copied
     * @deprecated use {@link #addOrUpdateResourcesForPaths(FileWorkArea, List, boolean)}
     */
    @Deprecated
    void addOrUpdateResources(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea);

    /**
     * Adds all of the given <b>files</b> and returns the resource names of all of them suitable for invoking
     * {@link #getResource(String)} and/or {@link #removeResource(String)}
     * 
     * @param workArea
     * @param files
     * @param removeFilesFromWorkArea
     * @return the resource names for each file that was uploaded. The resource names that are returned should be for each
     * file in <b>file</b>
     */
    List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea);
    
    /**
     * Removes the resource from the file service.
     * 
     * @param name - fully qualified path to the resource
     * @return true if the resource was removed
     */
    boolean removeResource(String name);
}
