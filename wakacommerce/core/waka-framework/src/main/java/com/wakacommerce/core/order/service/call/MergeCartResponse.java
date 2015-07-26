
package com.wakacommerce.core.order.service.call;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;

public class MergeCartResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Order order;

    private List<OrderItem> addedItems = new ArrayList<OrderItem>();;

    private List<OrderItem> removedItems = new ArrayList<OrderItem>();;

    private boolean merged;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(List<OrderItem> addedItems) {
        this.addedItems = addedItems;
    }

    public List<OrderItem> getRemovedItems() {
        return removedItems;
    }

    public void setRemovedItems(List<OrderItem> removedItems) {
        this.removedItems = removedItems;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

}
