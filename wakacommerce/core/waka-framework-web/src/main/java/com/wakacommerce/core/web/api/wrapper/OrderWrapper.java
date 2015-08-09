
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.OrderAdjustment;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderAttribute;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.call.ActivityMessageDTO;
import com.wakacommerce.core.payment.domain.OrderPayment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "order")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderWrapper extends BaseWrapper implements APIWrapper<Order> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String status;

    @XmlElement
    protected Money totalTax;

    @XmlElement
    protected Money totalShipping;

    @XmlElement
    protected Money subTotal;

    @XmlElement
    protected Money total;

    @XmlElement
    protected CustomerWrapper customer;

    @XmlElement(name = "orderItem")
    @XmlElementWrapper(name = "orderItems")
    protected List<OrderItemWrapper> orderItems;

    @XmlElement(name = "fulfillmentGroup")
    @XmlElementWrapper(name = "fulfillmentGroups")
    protected List<FulfillmentGroupWrapper> fulfillmentGroups;

    @XmlElement(name = "payment")
    @XmlElementWrapper(name = "payments")
    protected List<OrderPaymentWrapper> payments;

    @XmlElement(name = "orderAdjustment")
    @XmlElementWrapper(name = "orderAdjustments")
    protected List<AdjustmentWrapper> orderAdjustments;

    @XmlElement(name = "orderAttribute")
    @XmlElementWrapper(name = "orderAttributes")
    protected List<OrderAttributeWrapper> orderAttributes;

    @XmlElement(name = "cartMessages")
    @XmlElementWrapper(name = "cartMessages")
    protected List<CartMessageWrapper> cartMessages;

    @Override
    public void wrapDetails(Order model, HttpServletRequest request) {
        this.id = model.getId();

        if (model.getStatus() != null) {
            this.status = model.getStatus().getType();
        }

        this.totalTax = model.getTotalTax();
        this.totalShipping = model.getTotalShipping();
        this.subTotal = model.getSubTotal();
        this.total = model.getTotal();

        if (model.getOrderItems() != null && !model.getOrderItems().isEmpty()) {
            this.orderItems = new ArrayList<OrderItemWrapper>();
            for (OrderItem orderItem : model.getOrderItems()) {
                OrderItemWrapper orderItemWrapper = (OrderItemWrapper) context.getBean(OrderItemWrapper.class.getName());
                orderItemWrapper.wrapSummary(orderItem, request);
                this.orderItems.add(orderItemWrapper);
            }
        }

        if (model.getFulfillmentGroups() != null && !model.getFulfillmentGroups().isEmpty()) {
            this.fulfillmentGroups = new ArrayList<FulfillmentGroupWrapper>();
            for (FulfillmentGroup fulfillmentGroup : model.getFulfillmentGroups()) {
                FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                fulfillmentGroupWrapper.wrapSummary(fulfillmentGroup, request);
                this.fulfillmentGroups.add(fulfillmentGroupWrapper);
            }
        }

        if (model.getPayments() != null && !model.getPayments().isEmpty()) {
            this.payments = new ArrayList<OrderPaymentWrapper>();
            for (OrderPayment payment : model.getPayments()) {
                OrderPaymentWrapper paymentWrapper = (OrderPaymentWrapper) context.getBean(OrderPaymentWrapper.class.getName());
                paymentWrapper.wrapSummary(payment, request);
                this.payments.add(paymentWrapper);
            }
        }

        if (model.getOrderAdjustments() != null && !model.getOrderAdjustments().isEmpty()) {
            this.orderAdjustments = new ArrayList<AdjustmentWrapper>();
            for (OrderAdjustment orderAdjustment : model.getOrderAdjustments()) {
                AdjustmentWrapper orderAdjustmentWrapper = (AdjustmentWrapper) context.getBean(AdjustmentWrapper.class.getName());
                orderAdjustmentWrapper.wrapSummary(orderAdjustment, request);
                this.orderAdjustments.add(orderAdjustmentWrapper);
            }
        }
        if (model.getOrderAttributes() != null && !model.getOrderAttributes().isEmpty()) {
            Map<String, OrderAttribute> itemAttributes = model.getOrderAttributes();
            this.orderAttributes = new ArrayList<OrderAttributeWrapper>();
            Set<String> keys = itemAttributes.keySet();
            for (String key : keys) {
                OrderAttributeWrapper orderAttributeWrapper =
                        (OrderAttributeWrapper) context.getBean(OrderAttributeWrapper.class.getName());
                orderAttributeWrapper.wrapSummary(itemAttributes.get(key), request);
                this.orderAttributes.add(orderAttributeWrapper);
            }
        }
        CustomerWrapper customerWrapper = (CustomerWrapper) context.getBean(CustomerWrapper.class.getName());
        customerWrapper.wrapDetails(model.getCustomer(), request);
        this.customer = customerWrapper;

        if (model.getOrderMessages() != null && !model.getOrderMessages().isEmpty()) {
            for (ActivityMessageDTO dto : model.getOrderMessages()) {

                CartMessageWrapper cartMessageWrapper = (CartMessageWrapper) context.getBean(CartMessageWrapper.class.getName());
                cartMessageWrapper.wrapSummary(dto, request);
                if (cartMessages == null) {
                    cartMessages = new ArrayList<CartMessageWrapper>();
                }
                this.cartMessages.add(cartMessageWrapper);

            }
        }

    }

    @Override
    public void wrapSummary(Order model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Money getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Money totalTax) {
        this.totalTax = totalTax;
    }

    public Money getTotalShipping() {
        return totalShipping;
    }

    public void setTotalShipping(Money totalShipping) {
        this.totalShipping = totalShipping;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Money subTotal) {
        this.subTotal = subTotal;
    }

    public Money getTotal() {
        return total;
    }

    public void setTotal(Money total) {
        this.total = total;
    }

    public CustomerWrapper getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerWrapper customer) {
        this.customer = customer;
    }

    public List<OrderItemWrapper> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemWrapper> orderItems) {
        this.orderItems = orderItems;
    }

    public List<FulfillmentGroupWrapper> getFulfillmentGroups() {
        return fulfillmentGroups;
    }

    public void setFulfillmentGroups(List<FulfillmentGroupWrapper> fulfillmentGroups) {
        this.fulfillmentGroups = fulfillmentGroups;
    }

    public List<OrderPaymentWrapper> getPayments() {
        return payments;
    }

    public void setPayments(List<OrderPaymentWrapper> payments) {
        this.payments = payments;
    }

    public List<AdjustmentWrapper> getOrderAdjustments() {
        return orderAdjustments;
    }

    public void setOrderAdjustments(List<AdjustmentWrapper> orderAdjustments) {
        this.orderAdjustments = orderAdjustments;
    }

    public List<OrderAttributeWrapper> getOrderAttributes() {
        return orderAttributes;
    }

    public void setOrderAttributes(List<OrderAttributeWrapper> orderAttributes) {
        this.orderAttributes = orderAttributes;
    }

    public List<CartMessageWrapper> getCartMessages() {
        return cartMessages;
    }

    public void setCartMessages(List<CartMessageWrapper> cartMessages) {
        this.cartMessages = cartMessages;
    }


}
