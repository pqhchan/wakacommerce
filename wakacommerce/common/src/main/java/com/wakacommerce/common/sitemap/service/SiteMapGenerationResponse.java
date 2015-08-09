

package com.wakacommerce.common.sitemap.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @ hui
 */
public class SiteMapGenerationResponse {

    private String sitemapIndexFileName = "sitemap.xml";
    private List<String> siteMapFilePaths = new ArrayList<String>();
    private boolean hasError = false;
    private String errorCode;

    public String getSitemapIndexFileName() {
        return sitemapIndexFileName;
    }

    public void setSitemapIndexFileName(String sitemapIndexFileName) {
        this.sitemapIndexFileName = sitemapIndexFileName;
    }

    public List<String> getSiteMapFilePaths() {
        return siteMapFilePaths;
    }

    public void setSiteMapFilePaths(List<String> siteMapFilePaths) {
        this.siteMapFilePaths = siteMapFilePaths;
    }

    public boolean isHasError() {
        return hasError;
    }


    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
