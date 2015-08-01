
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.event.BroadleafApplicationEvent;


/**
 * An event for whenever an {@link OrderImpl} has been persisted
 *
 *     
 * 
 * @see {@link OrderPersistedEntityListener}
 */
public class OrderPersistedEvent extends BroadleafApplicationEvent {

    private static final long serialVersionUID = 1L;

    /**
     * @param order the newly persisted customer
     */
    public OrderPersistedEvent(Order order) {
        super(order);
    }
    
    /**
     * Gets the newly-persisted {@link Order} set by the {@link OrderPersistedEntityListener}
     * 
     * @return
     */
    public Order getOrder() {
        return (Order)source;
    }

}
