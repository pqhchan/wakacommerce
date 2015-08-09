
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

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

}
