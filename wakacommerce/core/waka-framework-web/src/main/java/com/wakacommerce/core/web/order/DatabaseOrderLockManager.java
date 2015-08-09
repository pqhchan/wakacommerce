
package com.wakacommerce.core.web.order;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.core.order.domain.NullOrderImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderLock;
import com.wakacommerce.core.order.service.OrderLockManager;
import com.wakacommerce.core.order.service.OrderService;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class DatabaseOrderLockManager implements OrderLockManager {

    protected static final Log LOG = LogFactory.getLog(DatabaseOrderLockManager.class);
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Override
    public Object acquireLock(Order order) {
        if (order == null || order instanceof NullOrderImpl) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Thread[" + Thread.currentThread().getId() + "] Attempted to grab a lock for a NullOrderImpl. ");
            }
            return order;
        }

        boolean lockAcquired = false;
        int count = 0;
        while (!lockAcquired) {
            try {
                lockAcquired = orderService.acquireLock(order);
            } catch (Exception e) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Couldn't acquire lock - that's ok, we'll retry shortly", e);
                }
            }

            if (!lockAcquired) {
                count++;
                if (count >= getDatabaseLockAcquisitionNumRetries()) {
                    LOG.warn(String.format("Exceeded max retries to attempt to acquire a lock on current Order (%s)", order.getId()));
                    throw new RuntimeException("Exceeded max retries to attempt to acquire a lock on current Order");
                }
                try {
                    long msToSleep = getDatabaseLockPollingIntervalMs();

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Thread[" + Thread.currentThread().getId() + "] Could not acquire order lock for order[" +
                                order.getId() + "] - sleeping for " + msToSleep + " ms");
                    }
                    Thread.sleep(msToSleep);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return order;
    }

    @Override
    public Object acquireLockIfAvailable(Order order) {
        if (order instanceof NullOrderImpl) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Attempted to grab a lock for a NullOrderImpl. Not blocking");
            }
            return order;
        }

        boolean lockAcquired = orderService.acquireLock(order); 
        return lockAcquired ? order : null;
    }

    @Override
    public void releaseLock(Object lockObject) {
        Order order = (Order) lockObject;
        if (order instanceof NullOrderImpl) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Thread[" + Thread.currentThread().getId() + "] Attempted to release a lock for a NullOrderImpl");
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Thread[" + Thread.currentThread().getId() + "] releasing lock for order[" + order.getId() + "]");
            }
            orderService.releaseLock(order);
        }
    }

    protected long getDatabaseLockPollingIntervalMs() {
        return BLCSystemProperty.resolveLongSystemProperty("order.lock.databaseLockPollingIntervalMs");
    }
    
    protected int getDatabaseLockAcquisitionNumRetries() {
        return BLCSystemProperty.resolveIntSystemProperty("order.lock.databaseLockAcquisitionNumRetries", 5);
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
