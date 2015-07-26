
package com.wakacommerce.core.order.service.call;

import java.util.ArrayList;
import java.util.List;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;

public class ReconstructCartResponse {

    private Order order;

    private List<OrderItem> removedItems = new ArrayList<OrderItem>();

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(List<OrderItem> removedItems) {
        this.removedItems = removedItems;
    }
}
