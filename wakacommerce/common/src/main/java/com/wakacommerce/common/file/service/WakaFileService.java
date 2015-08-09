package com.wakacommerce.common.file.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.wakacommerce.common.file.domain.FileWorkArea;

public interface WakaFileService {

    public FileWorkArea initializeWorkArea();

    void closeWorkArea(FileWorkArea workArea);

    File getResource(String name);

    File getResource(String name, Long timeout);

    File getLocalResource(String fullUrl);

    File getSharedLocalResource(String fullUrl);

    boolean checkForResourceOnClassPath(String name);

    InputStream getClasspathResource(String name);

    boolean removeResource(String name);

    String addOrUpdateResourceForPath(FileWorkArea workArea, File file, boolean removeFilesFromWorkArea);

    List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, boolean removeFilesFromWorkArea);

    List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea);

}
