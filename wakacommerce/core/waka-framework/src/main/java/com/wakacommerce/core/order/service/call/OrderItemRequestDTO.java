
package com.wakacommerce.core.order.service.call;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public class OrderItemRequestDTO {

    private Long skuId;
    private Long categoryId;
    private Long productId;
    private Long orderItemId;
    private Integer quantity;
    private Money overrideSalePrice;
    private Money overrideRetailPrice;
    private Map<String,String> itemAttributes = new HashMap<String,String>();
    private List<OrderItemRequestDTO> childOrderItems = new ArrayList<OrderItemRequestDTO>();
    private Long parentOrderItemId;
    
    public OrderItemRequestDTO() {}
    
    public OrderItemRequestDTO(Long productId, Integer quantity) {
        setProductId(productId);
        setQuantity(quantity);
    }
    
    public OrderItemRequestDTO(Long productId, Long skuId, Integer quantity) {
        setProductId(productId);
        setSkuId(skuId);
        setQuantity(quantity);
    }
    
    public OrderItemRequestDTO(Long productId, Long skuId, Long categoryId, Integer quantity) {
        setProductId(productId);
        setSkuId(skuId);
        setCategoryId(categoryId);
        setQuantity(quantity);
    }

    public Long getSkuId() {
        return skuId;
    }

    public OrderItemRequestDTO setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public OrderItemRequestDTO setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public OrderItemRequestDTO setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderItemRequestDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Map<String, String> getItemAttributes() {
        return itemAttributes;
    }

    public OrderItemRequestDTO setItemAttributes(Map<String, String> itemAttributes) {
        this.itemAttributes = itemAttributes;
        return this;
    }
    
    public Long getOrderItemId() {
        return orderItemId;
    }

    public OrderItemRequestDTO setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
        return this;
    }

    public Money getOverrideSalePrice() {
        return overrideSalePrice;
    }

    public void setOverrideSalePrice(Money overrideSalePrice) {
        this.overrideSalePrice = overrideSalePrice;
    }

    public Money getOverrideRetailPrice() {
        return overrideRetailPrice;
    }

    public void setOverrideRetailPrice(Money overrideRetailPrice) {
        this.overrideRetailPrice = overrideRetailPrice;
    }

    public List<OrderItemRequestDTO> getChildOrderItems() {
        return childOrderItems;
    }
    
    public void setChildOrderItems(List<OrderItemRequestDTO> childOrderItems) {
        this.childOrderItems = childOrderItems;
    }

    public Long getParentOrderItemId() {
        return parentOrderItemId;
    }

    public void setParentOrderItemId(Long parentOrderItemId) {
        this.parentOrderItemId = parentOrderItemId;
    }
    
}
