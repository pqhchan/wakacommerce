package com.wakacommerce.common.file.service;

public class WakaFileUtils {

    /**
     * 建立文件路径，确保在directory和fileName之间只有一个路径分隔符，
     * 该方法只适用于Unix相近系统以及URL
     * 
     * @param directory
     * @param fileName
     */
    public static String appendUnixPaths(String directory, String fileName) {
        if (directory.endsWith("/")) {
            return directory + removeLeadingUnixSlash(fileName);
        } else {
            return directory + addLeadingUnixSlash(fileName);
        }
    }

    /**
     * 文件名中如果有前导斜线，则删除
     */
    public static String removeLeadingUnixSlash(String fileName) {
        if (fileName.startsWith("/")) {
            return fileName.substring(1);
        }
        return fileName;
    }

    /**
     * 文件名如果没带前导斜线，则添加
     */
    public static String addLeadingUnixSlash(String fileName) {
        if (fileName.startsWith("/")) {
            return fileName;
        }
        return "/" + fileName;
    }
}
