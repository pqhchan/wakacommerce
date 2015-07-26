
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.order.domain.FulfillmentOption;

@XmlRootElement(name = "fulfillmentOption")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class FulfillmentOptionWrapper extends BaseWrapper implements APIWrapper<FulfillmentOption> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String name;

    @XmlElement
    protected String description;

    @XmlElement
    protected BroadleafEnumerationTypeWrapper fulfillmentType;

    @Override
    public void wrapDetails(FulfillmentOption model, HttpServletRequest request) {
        this.id = model.getId();
        if (model.getFulfillmentType() != null) {
            this.fulfillmentType = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class.getName());
            this.fulfillmentType.wrapDetails(model.getFulfillmentType(), request);
        }
        this.name = model.getName();
        this.description = model.getLongDescription();
    }

    @Override
    public void wrapSummary(FulfillmentOption model, HttpServletRequest request) {
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    
    /**
     * @return the fulfillmentType
     */
    public BroadleafEnumerationTypeWrapper getFulfillmentType() {
        return fulfillmentType;
    }

    
    /**
     * @param fulfillmentType the fulfillmentType to set
     */
    public void setFulfillmentType(BroadleafEnumerationTypeWrapper fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }

}
