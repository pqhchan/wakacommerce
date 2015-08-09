
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.event.BroadleafApplicationEvent;


/**
 *
 * @ hui
 */
public class OrderPersistedEvent extends BroadleafApplicationEvent {

    private static final long serialVersionUID = 1L;

    public OrderPersistedEvent(Order order) {
        super(order);
    }

    public Order getOrder() {
        return (Order)source;
    }

}
