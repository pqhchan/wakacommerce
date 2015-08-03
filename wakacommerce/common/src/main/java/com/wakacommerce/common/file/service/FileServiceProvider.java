package com.wakacommerce.common.file.service;

import java.io.File;
import java.util.List;

import com.wakacommerce.common.file.domain.FileWorkArea;
import com.wakacommerce.common.file.service.type.FileApplicationType;

public interface FileServiceProvider {
    
    File getResource(String url);

    File getResource(String url, FileApplicationType fileApplicationType);

    List<String> addOrUpdateResourcesForPaths(FileWorkArea workArea, List<File> files, boolean removeFilesFromWorkArea);
    
    boolean removeResource(String name);
    
}
