package com.wakacommerce.common.file.domain;

import java.io.File;

/**
 *
 * @ hui
 */
public class FileWorkArea {

    protected String filePathLocation;

    public String getFilePathLocation() {
        if (!filePathLocation.endsWith(File.separator)) {
            return filePathLocation + File.separator;
        } else {
            return filePathLocation;
        }
    }
    
    public void setFilePathLocation(String filePathLocation) {
        if (!filePathLocation.endsWith(File.separator)) {
            this.filePathLocation = filePathLocation + File.separator;
        } else {
            this.filePathLocation = filePathLocation;
        }
    }
    
}
