  
package com.wakacommerce.core.order.service;

import com.wakacommerce.core.order.domain.Order;

/**
 * It is recommended to only allow one write operation on orders at a time. For example, if two add to cart operations
 * for the same order came through at the same time, we would want to execute them serially and not in parallel.
 * 
 * Implementations of this interface are responsible for maintaining the lock status for the given order.
 * 
 * 
 */
public interface OrderLockManager {
    
    /**
     * Blocks the requesting thread until able to acquire a lock for the given order.
     * 
     * <b>NOTE</b>: Callers of this method MUST call {@link #releaseLock(Object)}, passing in the Object returned
     * from this call once their critical section has executed. The suggested idiom for this operation is:
     * 
     * Object lockObject = null;
     * try {
     *     lockObject = orderLockManager.acquireLock(order);
     *     // Do something 
     * } finally {
     *     orderLockManager.releaseLock(lockObject);
     * }
     * 
     * @param order
     * @return the lock object, which should be passed in as the parameter to {@link #releaseLock(Object)} once 
     *         the operation that required a lock has completed.
     */
    public Object acquireLock(Order order);

    /**
     * This method differs from {@link #acquireLock(Order)} in that it will not block if the lock is currently
     * held by a different caller. In the case that the lock was not able to be immediately acquired, this method
     * will return null.
     * 
     * @see #acquireLock(Order)
     * @param order
     * @return the lock if it was immediately acquireable, null otherwise
     */
    public Object acquireLockIfAvailable(Order order);
    
    /**
     * Releases the given lockObject and notifies any threads that are waiting on that object that they are able to
     * attempt to acquire the lock.
     * 
     * @param lockObject
     */
    public void releaseLock(Object lockObject);

    /**
     * This method indicates if the lock manager is active.  It can return a static value or a dynamic one 
     * based on values in the WakaRequestContext or other stateful mechanism.  A good example of when this might be 
     * dynamic is when there is a session-based lock and the request indicates that it is not OK to use sessions.
     * @return
     */
    public boolean isActive();
}
