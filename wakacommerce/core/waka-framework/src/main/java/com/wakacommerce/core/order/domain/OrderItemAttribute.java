
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.ValueAssignable;

/**
 * The Interface OrderItemAttribute.   Allows for arbitrary data to be
 * persisted with the orderItem.  This can be used to store additional
 * items that are required during order entry.
 *
 * Examples:
 *   Engravement Message for a jewelry item
 *   TestDate for someone purchasing an online exam
 *   Number of minutes for someone purchasing a rate plan.
 *
 */
public interface OrderItemAttribute extends ValueAssignable<String>, MultiTenantCloneable<OrderItemAttribute> {

    /**
     * Gets the id.
     * 
     * @return the id
     */
    Long getId();

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    void setId(Long id);

    /**
     * Gets the parent orderItem
     * 
     * @return the orderItem
     */
    OrderItem getOrderItem();

    /**
     * Sets the orderItem.
     * 
     * @param orderItem the associated orderItem
     */
    void setOrderItem(OrderItem orderItem);

    /**
     * Provide support for a deep copy of an order item.
     * @return
     */
    public OrderItemAttribute clone();
}
