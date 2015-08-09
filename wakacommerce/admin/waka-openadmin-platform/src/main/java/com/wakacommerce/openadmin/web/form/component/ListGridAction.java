package com.wakacommerce.openadmin.web.form.component;


/**
 *
 * @ hui
 */
public class ListGridAction implements Cloneable {
    
    public static final String ADD = "ADD";
    public static final String GEN_SKUS = "GEN_SKUS";
    public static final String REORDER = "REORDER";
    public static final String REMOVE = "REMOVE";
    public static final String UPDATE = "UPDATE";
    public static final String VIEW = "VIEW";

    protected String buttonClass = "";
    protected String urlPostfix = "";
    protected String iconClass = "";
    protected String displayText = "";
    protected String actionId = "";
    protected Boolean forListGridReadOnly = false;
    protected String actionUrlOverride = null;
    protected Boolean allCapable = false;
    protected Boolean singleActionOnly = false;
    
    public ListGridAction(String actionId) {
        this.actionId = actionId;
    }

    public ListGridAction withButtonClass(String buttonClass) {
        setButtonClass(buttonClass);
        return this;
    }

    public ListGridAction withUrlPostfix(String urlPostfix) {
        setUrlPostfix(urlPostfix);
        return this;
    }

    public ListGridAction withIconClass(String iconClass) {
        setIconClass(iconClass);
        return this;
    }

    public ListGridAction withDisplayText(String displayText) {
        setDisplayText(displayText);
        return this;
    }

    public ListGridAction withForListGridReadOnly(Boolean forListGridReadOnly) {
        setForListGridReadOnly(forListGridReadOnly);
        return this;
    }

    public ListGridAction withActionUrlOverride(String actionUrlOverride) {
        setActionUrlOverride(actionUrlOverride);
        return this;
    }

    public ListGridAction withAllCapable(Boolean allCapable) {
        setAllCapable(allCapable);
        return this;
    }

    public ListGridAction withSingleActionOnly(Boolean singleActionOnly) {
        setSingleActionOnly(singleActionOnly);
        return this;
    }
    
    public String getButtonClass() {
        return buttonClass + (allCapable ? " all-capable" : "") + (singleActionOnly ? " single-action-only" : "");
    }

    public Boolean getForListGridReadOnly() {
        return forListGridReadOnly;
    }

    public void setButtonClass(String buttonClass) {
        this.buttonClass = buttonClass;
    }
    
    public String getUrlPostfix() {
        return urlPostfix;
    }

    public void setUrlPostfix(String urlPostfix) {
        this.urlPostfix = urlPostfix;
    }
    
    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }
    
    public String getDisplayText() {
        return displayText;
    }
    
    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public void setForListGridReadOnly(Boolean forListGridReadOnly) {
        this.forListGridReadOnly = forListGridReadOnly;
    }

    public String getActionUrlOverride() {
        return actionUrlOverride;
    }

    public void setActionUrlOverride(String actionUrlOverride) {
        this.actionUrlOverride = actionUrlOverride;
    }

    public String getActionId() {
        return actionId;
    }

    public Boolean getAllCapable() {
        return allCapable;
    }

    public void setAllCapable(Boolean allCapable) {
        this.allCapable = allCapable;
    }

    public Boolean getSingleActionOnly() {
        return singleActionOnly;
    }

    public void setSingleActionOnly(Boolean singleActionOnly) {
        this.singleActionOnly = singleActionOnly;
    }

    @Override
    public ListGridAction clone() {
        ListGridAction cloned = new ListGridAction(actionId);
        cloned.buttonClass = buttonClass;
        cloned.displayText = displayText;
        cloned.iconClass = iconClass;
        cloned.urlPostfix = urlPostfix;
        cloned.forListGridReadOnly = forListGridReadOnly;
        cloned.allCapable = allCapable;
        cloned.actionId = actionId;
        cloned.actionUrlOverride = actionUrlOverride;
        cloned.singleActionOnly = singleActionOnly;
        return cloned;
    }
}
