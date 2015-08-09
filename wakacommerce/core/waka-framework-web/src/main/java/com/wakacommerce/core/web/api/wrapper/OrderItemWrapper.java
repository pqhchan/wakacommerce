
package com.wakacommerce.core.web.api.wrapper;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemAttribute;
import com.wakacommerce.core.order.domain.OrderItemPriceDetail;
import com.wakacommerce.core.order.domain.OrderItemQualifier;

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
@XmlRootElement(name = "orderItem")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderItemWrapper extends BaseWrapper implements APIWrapper<OrderItem> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected String name;

    @XmlElement
    protected Integer quantity;

    @XmlElement
    protected Money retailPrice;

    @XmlElement
    protected Money salePrice;

    @XmlElement
    protected Long orderId;

    @XmlElement
    protected Long categoryId;

    @XmlElement
    protected Long skuId;

    @XmlElement
    protected Long productId;
    @XmlElement
    protected Boolean isBundle = Boolean.FALSE;

    @XmlElement(name = "orderItemAttribute")
    @XmlElementWrapper(name = "orderItemAttributes")
    protected List<OrderItemAttributeWrapper> orderItemAttributes;
    
    @XmlElement(name = "orderItemPriceDetails")
    @XmlElementWrapper(name = "orderItemPriceDetails")
    protected List<OrderItemPriceDetailWrapper> orderItemPriceDetails;

    //This will only be poulated if this is a BundleOrderItem
    @XmlElement(name = "bundleItem")
    @XmlElementWrapper(name = "bundleItems")
    protected List<OrderItemWrapper> bundleItems;
    //

    @XmlElementWrapper(name = "qualifiers")
    @XmlElement(name = "qualifier")
    protected List<OrderItemQualifierWrapper> qualifiers;

    @XmlElement
    protected Boolean isDiscountingAllowed;

    @Override
    public void wrapDetails(OrderItem model, HttpServletRequest request) {
        this.id = model.getId();
        this.name = model.getName();
        this.quantity = model.getQuantity();
        this.orderId = model.getOrder().getId();
        this.retailPrice = model.getRetailPrice();
        this.salePrice = model.getSalePrice();

        if (model.getCategory() != null) {
            this.categoryId = model.getCategory().getId();
        }

        if (model.getOrderItemAttributes() != null && !model.getOrderItemAttributes().isEmpty()) {
            Map<String, OrderItemAttribute> itemAttributes = model.getOrderItemAttributes();
            this.orderItemAttributes = new ArrayList<OrderItemAttributeWrapper>();
            Set<String> keys = itemAttributes.keySet();
            for (String key : keys) {
                OrderItemAttributeWrapper orderItemAttributeWrapper = 
                        (OrderItemAttributeWrapper) context.getBean(OrderItemAttributeWrapper.class.getName());
                orderItemAttributeWrapper.wrapSummary(itemAttributes.get(key), request);
                this.orderItemAttributes.add(orderItemAttributeWrapper);
            }
        }
        if (model.getOrderItemPriceDetails() != null && !model.getOrderItemPriceDetails().isEmpty()) {
            this.orderItemPriceDetails = new ArrayList<OrderItemPriceDetailWrapper>();
            for (OrderItemPriceDetail orderItemPriceDetail : model.getOrderItemPriceDetails()) {
                OrderItemPriceDetailWrapper orderItemPriceDetailWrapper =
                        (OrderItemPriceDetailWrapper) context.getBean(OrderItemPriceDetailWrapper.class.getName());
                orderItemPriceDetailWrapper.wrapSummary(orderItemPriceDetail, request);
                this.orderItemPriceDetails.add(orderItemPriceDetailWrapper);
            }
        }
        
        if (model instanceof DiscreteOrderItem) {
            DiscreteOrderItem doi = (DiscreteOrderItem) model;
            this.skuId = doi.getSku().getId();
            this.productId = doi.getProduct().getId();
            this.isBundle = false;
            this.isDiscountingAllowed = doi.isDiscountingAllowed();
        } else if (model instanceof BundleOrderItem) {
            BundleOrderItem boi = (BundleOrderItem) model;
            this.skuId = boi.getSku().getId();
            this.productId = boi.getProduct().getId();
            this.isBundle = true;
            this.isDiscountingAllowed = boi.isDiscountingAllowed();
            //Wrap up all the discrete order items for this bundle order item
            List<DiscreteOrderItem> discreteItems = boi.getDiscreteOrderItems();
            if (discreteItems != null && !discreteItems.isEmpty()) {
                this.bundleItems = new ArrayList<OrderItemWrapper>();
                for (DiscreteOrderItem doi : discreteItems) {
                    OrderItemWrapper doiWrapper = (OrderItemWrapper) context.getBean(OrderItemWrapper.class.getName());
                    doiWrapper.wrapSummary(doi, request);
                    this.bundleItems.add(doiWrapper);
                }
            }
            
        }

        if (model.getOrderItemQualifiers() != null && !model.getOrderItemQualifiers().isEmpty()) {
            this.qualifiers = new ArrayList<OrderItemQualifierWrapper>();
            for (OrderItemQualifier qualifier : model.getOrderItemQualifiers()) {
                OrderItemQualifierWrapper qualifierWrapper = (OrderItemQualifierWrapper) context.getBean(OrderItemQualifierWrapper.class.getName());
                qualifierWrapper.wrapSummary(qualifier, request);
                this.qualifiers.add(qualifierWrapper);
            }
        }
    }

    @Override
    public void wrapSummary(OrderItem model, HttpServletRequest request) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Money getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Money retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Money getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Money salePrice) {
        this.salePrice = salePrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getIsBundle() {
        return isBundle;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public List<OrderItemAttributeWrapper> getOrderItemAttributes() {
        return orderItemAttributes;
    }

    public void setOrderItemAttributes(List<OrderItemAttributeWrapper> orderItemAttributes) {
        this.orderItemAttributes = orderItemAttributes;
    }

    public List<OrderItemPriceDetailWrapper> getOrderItemPriceDetails() {
        return orderItemPriceDetails;
    }

    public void setOrderItemPriceDetails(List<OrderItemPriceDetailWrapper> orderItemPriceDetails) {
        this.orderItemPriceDetails = orderItemPriceDetails;
    }

    public List<OrderItemWrapper> getBundleItems() {
        return bundleItems;
    }

    public void setBundleItems(List<OrderItemWrapper> bundleItems) {
        this.bundleItems = bundleItems;
    }

    public List<OrderItemQualifierWrapper> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<OrderItemQualifierWrapper> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public Boolean getIsDiscountingAllowed() {
        return isDiscountingAllowed;
    }

    public void setIsDiscountingAllowed(Boolean isDiscountingAllowed) {
        this.isDiscountingAllowed = isDiscountingAllowed;
    }

}
