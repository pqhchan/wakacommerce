
package com.wakacommerce.core.order.domain;

import java.util.List;

public interface GiftWrapOrderItem extends DiscreteOrderItem {

    public List<OrderItem> getWrappedItems();

    public void setWrappedItems(List<OrderItem> wrappedItems);

}
