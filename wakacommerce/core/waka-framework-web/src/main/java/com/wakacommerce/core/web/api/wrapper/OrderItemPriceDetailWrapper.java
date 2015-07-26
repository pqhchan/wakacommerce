
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.OrderItemPriceDetailAdjustment;
import com.wakacommerce.core.order.domain.OrderItemPriceDetail;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * API wrapper to wrap Order Item Price Details.
 *Priyesh Patel
 *
 */
@XmlRootElement(name = "orderItemAttribute")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderItemPriceDetailWrapper extends BaseWrapper implements
        APIWrapper<OrderItemPriceDetail> {
    
    @XmlElement
    protected Long id;
    
    @XmlElement
    protected Money totalAdjustmentValue;

    @XmlElement
    protected Money totalAdjustedPrice;

    @XmlElement
    protected Integer quantity;
    @XmlElement(name = "adjustment")
    @XmlElementWrapper(name = "adjustments")
    protected List<AdjustmentWrapper> orderItemPriceDetailAdjustments = new LinkedList<AdjustmentWrapper>();

    @Override
    public void wrapDetails(OrderItemPriceDetail model, HttpServletRequest request) {
        this.id = model.getId();
        this.quantity = model.getQuantity();
        this.totalAdjustmentValue = model.getTotalAdjustmentValue();
        this.totalAdjustedPrice = model.getTotalAdjustedPrice();
        if (!model.getOrderItemPriceDetailAdjustments().isEmpty()) {
            this.orderItemPriceDetailAdjustments = new ArrayList<AdjustmentWrapper>();
            for (OrderItemPriceDetailAdjustment orderItemPriceDetail : model.getOrderItemPriceDetailAdjustments()) {
                AdjustmentWrapper orderItemPriceDetailAdjustmentWrapper =
                        (AdjustmentWrapper) context.getBean(AdjustmentWrapper.class.getName());
                orderItemPriceDetailAdjustmentWrapper.wrapSummary(orderItemPriceDetail, request);
                this.orderItemPriceDetailAdjustments.add(orderItemPriceDetailAdjustmentWrapper);
            }
        }
    }
    
    @Override
    public void wrapSummary(OrderItemPriceDetail model, HttpServletRequest request) {
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
     * @return the totalAdjustmentValue
     */
    public Money getTotalAdjustmentValue() {
        return totalAdjustmentValue;
    }

    
    /**
     * @param totalAdjustmentValue the totalAdjustmentValue to set
     */
    public void setTotalAdjustmentValue(Money totalAdjustmentValue) {
        this.totalAdjustmentValue = totalAdjustmentValue;
    }

    
    /**
     * @return the totalAdjustedPrice
     */
    public Money getTotalAdjustedPrice() {
        return totalAdjustedPrice;
    }

    
    /**
     * @param totalAdjustedPrice the totalAdjustedPrice to set
     */
    public void setTotalAdjustedPrice(Money totalAdjustedPrice) {
        this.totalAdjustedPrice = totalAdjustedPrice;
    }

    
    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    
    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    
    /**
     * @return the orderItemPriceDetailAdjustments
     */
    public List<AdjustmentWrapper> getOrderItemPriceDetailAdjustments() {
        return orderItemPriceDetailAdjustments;
    }

    
    /**
     * @param orderItemPriceDetailAdjustments the orderItemPriceDetailAdjustments to set
     */
    public void setOrderItemPriceDetailAdjustments(List<AdjustmentWrapper> orderItemPriceDetailAdjustments) {
        this.orderItemPriceDetailAdjustments = orderItemPriceDetailAdjustments;
    }
}
