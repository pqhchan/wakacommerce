
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.CategoryAttribute;


/**
 * 
 *  
 */
@XmlRootElement(name = "categoryAttribute")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryAttributeWrapper extends BaseWrapper implements APIWrapper<CategoryAttribute>{

    @XmlElement
    protected Long id;

    @XmlElement
    protected Long categoryId;

    @XmlElement
    protected String attributeName;

    @XmlElement
    protected String attributeValue;

    @Override
    public void wrapDetails(CategoryAttribute model, HttpServletRequest request) {
        this.id = model.getId();
        this.categoryId = model.getCategory().getId();
        this.attributeName = model.getName();
        this.attributeValue = model.getValue();
    }

    @Override
    public void wrapSummary(CategoryAttribute model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    
    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    
    /**
     * @return the attributeName
     */
    public String getAttributeName() {
        return attributeName;
    }

    
    /**
     * @param attributeName the attributeName to set
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    
    /**
     * @return the attributeValue
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    
    /**
     * @param attributeValue the attributeValue to set
     */
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
}
