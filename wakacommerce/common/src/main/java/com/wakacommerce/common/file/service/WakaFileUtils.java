package com.wakacommerce.common.file.service;

public class WakaFileUtils {

    public static String appendUnixPaths(String directory, String fileName) {
        if (directory.endsWith("/")) {
            return directory + removeLeadingUnixSlash(fileName);
        } else {
            return directory + addLeadingUnixSlash(fileName);
        }
    }

    public static String removeLeadingUnixSlash(String fileName) {
        if (fileName.startsWith("/")) {
            return fileName.substring(1);
        }
        return fileName;
    }

    public static String addLeadingUnixSlash(String fileName) {
        if (fileName.startsWith("/")) {
            return fileName;
        }
        return "/" + fileName;
    }
}
