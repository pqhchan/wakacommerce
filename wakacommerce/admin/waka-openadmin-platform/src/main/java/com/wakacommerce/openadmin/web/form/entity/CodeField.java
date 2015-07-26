
package com.wakacommerce.openadmin.web.form.entity;



/**
 *Andre Azzolini (apazzolini)
 */
public class CodeField extends Field {

    protected String fileType;
    protected String fetchUrlSuffix;

    /* ************************** */
    /* STANDARD GETTERS / SETTERS */
    /* ************************** */
    
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    
    public String getFetchUrlSuffix() {
        return fetchUrlSuffix;
    }
    
    public void setFetchUrlSuffix(String fetchUrlSuffix) {
        this.fetchUrlSuffix = fetchUrlSuffix;
    }

}
