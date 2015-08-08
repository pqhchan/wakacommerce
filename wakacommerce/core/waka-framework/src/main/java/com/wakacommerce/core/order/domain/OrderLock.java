  
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

/**
 * Domain object used to synchronize {@link Order} operations.
 * 
 * 
 */
public interface OrderLock extends Serializable {

    /**
     * @return the id of the {@link Order} associated with this OrderLock
     */
    public Long getOrderId();

    /**
     * Sets the id of the {@link Order} associated with this OrderLock
     * 
     * @param orderId
     */
    public void setOrderId(Long orderId);

    /**
     * @return whether or not this OrderLock is currently locked
     */
    public Boolean getLocked();

    /**
     * Sets the lock state of this OrderLock
     * 
     * @param locked
     */
    public void setLocked(Boolean locked);

    /**
     * @return the last time this lock record was successfully altered
     */
    Long getLastUpdated();

    /**
     * Set the time of alteration
     *
     * @param lastUpdated
     */
    void setLastUpdated(Long lastUpdated);

    /**
     * @return the key used to identify the creator of the lock
     */
    String getKey();

    /**
     * Set a key identifying the creator of the lock
     *
     * @param nodeKey
     */
    void setKey(String nodeKey);
}