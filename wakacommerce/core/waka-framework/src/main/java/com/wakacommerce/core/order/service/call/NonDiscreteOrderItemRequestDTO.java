
package com.wakacommerce.core.order.service.call;

import com.wakacommerce.common.money.Money;

public class NonDiscreteOrderItemRequestDTO extends OrderItemRequestDTO {

    protected String itemName;

    public NonDiscreteOrderItemRequestDTO() {
    }

    public NonDiscreteOrderItemRequestDTO(String itemName, Integer quantity, Money overrideRetailPrice) {
        setItemName(itemName);
        setQuantity(quantity);
        setOverrideRetailPrice(overrideRetailPrice);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
