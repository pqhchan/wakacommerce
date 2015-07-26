
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 * The Interface OrderAttribute.   Allows for arbitrary data to be
 * persisted with the order.
 *
 */
public interface OrderAttribute extends Serializable, MultiTenantCloneable<OrderAttribute> {

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
     * Gets the value.
     * 
     * @return the value
     */
    String getValue();

    /**
     * Sets the value.
     * 
     * @param value the new value
     */
    void setValue(String value);   

    /**
     * Gets the associated order.
     * 
     * @return the order
     */
    Order getOrder();

    /**
     * Sets the order.
     * 
     * @param order the associated order
     */
    void setOrder(Order order);

    /**
     * Gets the name.
     * 
     * @return the name
     */
    String getName();

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    void setName(String name);
}
