
package com.wakacommerce.common.page.dto;

import org.apache.commons.beanutils.BeanUtils;

import com.wakacommerce.common.structure.dto.ItemCriteriaDTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Page fields must be pre-processed (for example to fix image paths).
 * This DTO allows us to process the PageFields once and then cache
 * the results.
 */
public class PageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Long id;
    protected String description;
    protected String localeCode;
    protected String templatePath;
    protected String url;
    protected Map<String, Object> pageFields = new HashMap<String, Object>();
    protected String ruleExpression;
    protected List<ItemCriteriaDTO> itemCriteriaDTOList;
    protected Map<String, String> pageAttributes = new HashMap<String, String>();
    protected Map<String, Object> foreignPageFields = new HashMap<String, Object>();

    /**
     * Attempts to obtain the given property value from the dynamic property map first, and then an actual bean property
     * via a getter
     * 
     * @param propertyName
     * @return
     */
    public Object getPropertyValue(String propertyName) {
        if (getPageFields().containsKey(propertyName)) {
            return getPageFields().get(propertyName);
        } else if (getPageAttributes().containsKey(propertyName)) {
            return getPageAttributes().get(propertyName);
        } else {
            try {
                return BeanUtils.getProperty(this, propertyName);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getPageFields() {
        return pageFields;
    }

    public void setPageFields(Map<String, Object> pageFields) {
        this.pageFields = pageFields;
    }
    
    public String getRuleExpression() {
        return ruleExpression;
    }

    public void setRuleExpression(String ruleExpression) {
        this.ruleExpression = ruleExpression;
    }

    public List<ItemCriteriaDTO> getItemCriteriaDTOList() {
        return itemCriteriaDTOList;
    }

    public void setItemCriteriaDTOList(List<ItemCriteriaDTO> itemCriteriaDTOList) {
        this.itemCriteriaDTOList = itemCriteriaDTOList;
    }

    public Map<String, String> getPageAttributes() {
        return pageAttributes;
    }
    
    public void setPageAttributes(Map<String, String> pageAttributes) {
        this.pageAttributes = pageAttributes;
    }   
    
    public Map<String, Object> getForeignPageFields() {
        return foreignPageFields;
    }
    
    public void setForeignPageFields(Map<String, Object> foreignPageFields) {
        this.foreignPageFields = foreignPageFields;
    }

    public void copy(PageDTO original) {
        description = original.description;
        id = original.id;
        localeCode = original.localeCode;
        templatePath = original.templatePath;
        url = original.url;
        
        // Extension Handlers Might Modify This
        pageFields = new HashMap<String, Object>(original.pageFields);
        ruleExpression = original.ruleExpression;
        itemCriteriaDTOList = original.itemCriteriaDTOList;
        pageAttributes = original.pageAttributes;
    }
    
}