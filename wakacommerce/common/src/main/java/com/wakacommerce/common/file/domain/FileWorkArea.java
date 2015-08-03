package com.wakacommerce.common.file.domain;

import java.io.File;

/**
 * 临时工作区域
 */
public class FileWorkArea {

    protected String filePathLocation;
    
    /**
     * 该工作区域在系统中的位置，位置以特定系统上的路径分隔符结束
     */
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
