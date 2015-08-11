package com.wakacommerce.profile.core.domain;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.wakacommerce.common.util.ApplicationContextHolder;


/**
 *
 * @ hui
 */
public class CustomerPersistedEntityListener {

    @PostPersist
    @PostUpdate
    public void customerUpdated(final Object entity) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    ApplicationContextHolder.getApplicationContext().publishEvent(new CustomerPersistedEvent((Customer) entity));
                }
            });
        }
    }
    
}
