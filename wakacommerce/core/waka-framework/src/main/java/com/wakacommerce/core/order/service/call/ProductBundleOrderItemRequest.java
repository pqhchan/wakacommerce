
package com.wakacommerce.core.order.service.call;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.ProductBundle;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.order.domain.Order;

import java.util.HashMap;
import java.util.Map;

public class ProductBundleOrderItemRequest {

    protected String name;
    protected Category category;
    protected Sku sku;
    protected Order order;
    protected int quantity;
    protected ProductBundle productBundle;
    private Map<String,String> itemAttributes = new HashMap<String,String>();
    protected Money salePriceOverride;
    protected Money retailPriceOverride;

    public ProductBundleOrderItemRequest() {}
    
    public String getName() {
        return name;
    }

    public ProductBundleOrderItemRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ProductBundleOrderItemRequest setCategory(Category category) {
        this.category = category;
        return this;
    }
    
    public Sku getSku() {
        return sku;
    }

    public ProductBundleOrderItemRequest setSku(Sku sku) {
        this.sku = sku;
        return this;
    }
    
    public ProductBundleOrderItemRequest setOrder(Order order) {
        this.order = order;
        return this;
    }
    
    public Order getOrder() {
        return order;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductBundleOrderItemRequest setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductBundle getProductBundle() {
        return productBundle;
    }

    public ProductBundleOrderItemRequest setProductBundle(ProductBundle productBundle) {
        this.productBundle = productBundle;
        return this;
    }

    public Map<String, String> getItemAttributes() {
        return itemAttributes;
    }

    public ProductBundleOrderItemRequest setItemAttributes(Map<String, String> itemAttributes) {
        this.itemAttributes = itemAttributes;
        return this;
    }

    public Money getSalePriceOverride() {
        return salePriceOverride;
    }

    public void setSalePriceOverride(Money salePriceOverride) {
        this.salePriceOverride = salePriceOverride;
    }

    public Money getRetailPriceOverride() {
        return retailPriceOverride;
    }

    public void setRetailPriceOverride(Money retailPriceOverride) {
        this.retailPriceOverride = retailPriceOverride;
    }

}
