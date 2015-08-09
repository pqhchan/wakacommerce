
package com.wakacommerce.core.order.service.call;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.PersonalMessage;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public abstract class AbstractOrderItemRequest {

    protected Sku sku;
    protected Category category;
    protected Product product;
    protected Order order;
    protected int quantity;
    protected Money salePriceOverride;
    protected Money retailPriceOverride;
    protected PersonalMessage personalMessage;
    protected Map<String,String> itemAttributes = new HashMap<String,String>();
    
    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Map<String, String> getItemAttributes() {
        return itemAttributes;
    }

    public void setItemAttributes(Map<String, String> itemAttributes) {
        this.itemAttributes = itemAttributes;
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

    protected void copyProperties(AbstractOrderItemRequest newRequest) {
        newRequest.setCategory(category);
        newRequest.setItemAttributes(itemAttributes);
        newRequest.setPersonalMessage(personalMessage);
        newRequest.setProduct(product);
        newRequest.setQuantity(quantity);
        newRequest.setSku(sku);
        newRequest.setOrder(order);
        newRequest.setSalePriceOverride(salePriceOverride);
        newRequest.setRetailPriceOverride(retailPriceOverride);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        AbstractOrderItemRequest that = (AbstractOrderItemRequest) o;

        if (quantity != that.quantity) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        if (salePriceOverride != null ? !salePriceOverride.equals(that.salePriceOverride) : that.salePriceOverride != null)
            return false;
        if (sku != null ? !sku.equals(that.sku) : that.sku != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sku != null ? sku.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + quantity;
        result = 31 * result + (salePriceOverride != null ? salePriceOverride.hashCode() : 0);
        return result;
    }

    public PersonalMessage getPersonalMessage() {
        return personalMessage;
    }

    public void setPersonalMessage(PersonalMessage personalMessage) {
        this.personalMessage = personalMessage;
    }
}
