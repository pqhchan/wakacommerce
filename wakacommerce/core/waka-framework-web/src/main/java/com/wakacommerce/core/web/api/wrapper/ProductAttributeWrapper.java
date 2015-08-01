
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.catalog.domain.ProductAttribute;

/**
 * To change this template use File | Settings | File Templates.
 * <p/>
 * User:   
 * Date: 4/10/12
 */
@XmlRootElement(name = "productAttribute")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductAttributeWrapper extends BaseWrapper implements APIWrapper<ProductAttribute>{

    @XmlElement
    protected Long id;

    @XmlElement
    protected Long productId;

    @XmlElement
    protected String attributeName;

    @XmlElement
    protected String attributeValue;

    @Override
    public void wrapDetails(ProductAttribute model, HttpServletRequest request) {
        this.id = model.getId();
        this.productId = model.getProduct().getId();
        this.attributeName = model.getName();
        this.attributeValue = model.getValue();
    }

    @Override
    public void wrapSummary(ProductAttribute model, HttpServletRequest request) {
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
     * @return the productId
     */
    public Long getProductId() {
        return productId;
    }

    
    /**
     * @param productId the productId to set
     */
    public void setProductId(Long productId) {
        this.productId = productId;
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
