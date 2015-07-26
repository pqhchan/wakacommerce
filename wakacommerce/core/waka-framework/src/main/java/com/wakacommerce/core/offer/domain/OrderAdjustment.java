
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.core.order.domain.Order;

public interface OrderAdjustment extends Adjustment {

    public Order getOrder();

    public void init(Order order, Offer offer, String reason);

    public void setOrder(Order order);

}
