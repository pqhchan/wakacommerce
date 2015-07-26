
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.order.domain.OrderItemQualifier;

@XmlRootElement(name = "orderItemQualifier")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderItemQualifierWrapper extends BaseWrapper implements APIWrapper<OrderItemQualifier> {

    @XmlElement
    protected Long offerId;

    @XmlElement
    protected Long quantity;

    @Override
    public void wrapDetails(OrderItemQualifier model, HttpServletRequest request) {
        this.offerId = model.getOffer().getId();
        this.quantity = model.getQuantity();
    }

    @Override
    public void wrapSummary(OrderItemQualifier model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    
    /**
     * @return the offerId
     */
    public Long getOfferId() {
        return offerId;
    }

    
    /**
     * @param offerId the offerId to set
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    
    /**
     * @return the quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
