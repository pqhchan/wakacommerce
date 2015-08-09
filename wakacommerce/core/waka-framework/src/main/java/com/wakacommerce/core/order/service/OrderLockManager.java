
package com.wakacommerce.core.order.service;

import com.wakacommerce.core.order.domain.Order;

/**
 *
 * @ hui
 */
public interface OrderLockManager {

    public Object acquireLock(Order order);

    public Object acquireLockIfAvailable(Order order);

    public void releaseLock(Object lockObject);

    public boolean isActive();
}
