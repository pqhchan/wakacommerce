
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BroadleafEnumerationTypeWrapper getFulfillmentType() {
        return fulfillmentType;
    }

    public void setFulfillmentType(BroadleafEnumerationTypeWrapper fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
    }

}
