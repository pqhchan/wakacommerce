
package com.wakacommerce.core.order.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.wakacommerce.common.util.ApplicationContextHolder;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;


/**
 *
 * @ hui
 */
public class OrderPersistedEntityListener {

    @PostPersist
    @PostUpdate
    public void customerUpdated(final Object entity) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    ApplicationContextHolder.getApplicationContext().publishEvent(new OrderPersistedEvent((Order) entity));
                }
            });
        }
    }
    
}
