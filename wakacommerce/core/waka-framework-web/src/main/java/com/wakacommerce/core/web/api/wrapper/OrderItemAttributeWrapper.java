
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.order.domain.OrderItemAttribute;

/**
 * API wrapper to wrap Order Item Attributes.
 *  
 *
 */
@XmlRootElement(name = "orderItemAttribute")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderItemAttributeWrapper extends BaseWrapper implements
        APIWrapper<OrderItemAttribute> {
    
    @XmlElement
    protected Long id;
    
    @XmlElement
    protected String name;
    
    @XmlElement
    protected String value;
    
    @XmlElement
    protected Long orderItemId;

    @Override
    public void wrapDetails(OrderItemAttribute model, HttpServletRequest request) {
        this.id = model.getId();
        this.name = model.getName();
        this.value = model.getValue();
        this.orderItemId = model.getOrderItem().getId();
    }
    
    @Override
    public void wrapSummary(OrderItemAttribute model, HttpServletRequest request) {
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    
    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    
    /**
     * @return the orderItemId
     */
    public Long getOrderItemId() {
        return orderItemId;
    }

    
    /**
     * @param orderItemId the orderItemId to set
     */
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }
}
