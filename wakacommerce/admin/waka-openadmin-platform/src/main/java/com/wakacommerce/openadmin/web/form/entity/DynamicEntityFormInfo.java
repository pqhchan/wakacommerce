package com.wakacommerce.openadmin.web.form.entity;

/**
 *
 * @ hui
 */
public class DynamicEntityFormInfo {

    public static final String FIELD_SEPARATOR = "|";
    
    protected String criteriaName;
    protected String propertyName;
    protected String propertyValue;
    protected String ceilingClassName;
    protected String securityCeilingClassName;
    protected String[] customCriteriaOverride;
    
    public DynamicEntityFormInfo withCriteriaName(String criteriaName) {
        setCriteriaName(criteriaName);
        return this;
    }

    public DynamicEntityFormInfo withPropertyName(String propertyName) {
        setPropertyName(propertyName);
        return this;
    }
    
    public DynamicEntityFormInfo withPropertyValue(String propertyValue) {
        setPropertyValue(propertyValue);
        return this;
    }

    public DynamicEntityFormInfo withCeilingClassName(String ceilingClassName) {
        setCeilingClassName(ceilingClassName);
        return this;
    }

    public DynamicEntityFormInfo withSecurityCeilingClassName(String securityCeilingClassName) {
        setSecurityCeilingClassName(securityCeilingClassName);
        return this;
    }

    public DynamicEntityFormInfo withCustomCriteriaOverride(String[] customCriteriaOverride) {
        setCustomCriteriaOverride(customCriteriaOverride);
        return this;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    
    public String getPropertyValue() {
        return propertyValue;
    }
    
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getCeilingClassName() {
        return ceilingClassName;
    }

    public String getSecurityCeilingClassName() {
        return securityCeilingClassName;
    }

    public void setCeilingClassName(String ceilingClassName) {
        this.ceilingClassName = ceilingClassName;
    }

    public void setSecurityCeilingClassName(String securityCeilingClassName) {
        this.securityCeilingClassName = securityCeilingClassName;
    }
    
    public String[] getCustomCriteriaOverride() {
        return customCriteriaOverride;
    }

    public void setCustomCriteriaOverride(String[] customCriteriaOverride) {
        this.customCriteriaOverride = customCriteriaOverride;
    }

}
