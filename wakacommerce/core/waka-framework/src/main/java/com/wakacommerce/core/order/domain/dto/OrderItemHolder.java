
package com.wakacommerce.core.order.domain.dto;

import com.wakacommerce.core.order.domain.OrderItem;

/**
 * Class that contains a reference to an OrderItem
 *bpolster
 *
 */
public class OrderItemHolder {

    private OrderItem orderItem;

    public OrderItemHolder(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

}
