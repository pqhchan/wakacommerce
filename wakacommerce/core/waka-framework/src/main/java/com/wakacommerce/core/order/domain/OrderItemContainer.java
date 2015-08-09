
package com.wakacommerce.core.order.domain;

import java.util.List;


public interface OrderItemContainer {

    List<? extends OrderItem> getOrderItems();

    boolean getAllowDiscountsOnChildItems();

    boolean isPricingAtContainerLevel();
}
