package com.wakacommerce.openadmin.web.form.component;

public class ListGridRecordIcon {
    
    protected String cssClass;
    protected String message;
    
    public ListGridRecordIcon withCssClass(String cssClass) {
        setCssClass(cssClass);
        return this;
    }

    public ListGridRecordIcon withMessage(String message) {
        setMessage(message);
        return this;
    }

    public String getCssClass() {
        return cssClass;
    }
    
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
