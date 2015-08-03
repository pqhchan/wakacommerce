package com.wakacommerce.openadmin.web.form.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class EntityFormAction implements Cloneable {
    
    public static final String ADD = "ADD";
    public static final String SAVE = "SAVE";
    public static final String DELETE = "DELETE";
    public static final String PREVIEW = "PREVIEW";

    protected String buttonType = "button";
    protected String buttonClass = "";
    protected String urlPostfix = "";
    protected String iconClass = "";
    protected String displayText = "";
    protected String id = "";
    protected String urlOverride = null;
    
    public EntityFormAction(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
            EntityFormAction that = (EntityFormAction) obj;
            return new EqualsBuilder()
                .append(buttonClass, that.buttonClass)
                .append(urlPostfix, that.urlPostfix)
                .append(iconClass, that.iconClass)
                .append(displayText, that.displayText)
                .build();
        }
        return false;
    }
    
    @Override
    public EntityFormAction clone() {
        EntityFormAction cloned = new EntityFormAction(id);
        cloned.buttonType = buttonType;
        cloned.buttonClass = buttonClass;
        cloned.urlPostfix = urlPostfix;
        cloned.iconClass = iconClass;
        cloned.displayText = displayText;
        return cloned;
    }
    
    public EntityFormAction withButtonType(String buttonType) {
        setButtonType(buttonType);
        return this;
    }
    
    public EntityFormAction withButtonClass(String buttonClass) {
        setButtonClass(buttonClass);
        return this;
    }
    
    public EntityFormAction withUrlPostfix(String urlPostfix) {
        setUrlPostfix(urlPostfix);
        return this;
    }
    
    public EntityFormAction withIconClass(String iconClass) {
        setIconClass(iconClass);
        return this;
    }
    
    public EntityFormAction withDisplayText(String displayText) {
        setDisplayText(displayText);
        return this;
    }
    
    public EntityFormAction withUrlOverride(String urlOverride) {
        setUrlOverride(urlOverride);
        return this;
    }
    
    public String getId() {
        return id;
    }

    public String getButtonType() {
        return buttonType;
    }
    
    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonClass() {
        return buttonClass;
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
    
    /**
     * Gets the manual override for the data-actionurl attribute on an action.
     * 
     * @return
     */
    public String getUrlOverride() {
        return urlOverride;
    }
    
    /**
     * This is a manual override for the data-actionurl attribute for an listgrid action. The data-actionurl attribute on a
     * button is normally automatically computed by appending the postfix URL to the path of the list grid
     * 
     * @param actionUrlOverride
     */
    public void setUrlOverride(String urlOverride) {
        this.urlOverride = urlOverride;
    }

}
