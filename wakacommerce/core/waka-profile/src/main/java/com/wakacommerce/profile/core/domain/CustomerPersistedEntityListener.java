
package com.wakacommerce.profile.core.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.wakacommerce.common.util.ApplicationContextHolder;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;


/**
 * The main function of this entity listener is to publish a Spring event that the customer has been persisted. This is
 * necessary in order to update the current customer in the application
 *
 *     
 * 
 * @see {@link ApplicationEventPublisher#publishEvent(org.springframework.context.ApplicationEvent)}
 * @see {@link CustomerPersistedEvent}
 * @see {@link com.wakacommerce.profile.web.core.CustomerStateRefresher}
 * @see {@link com.wakacommerce.profile.web.core.CustomerState}
 */
public class CustomerPersistedEntityListener {
    
    /**
     * Invoked on both the PostPersist and PostUpdate. The default implementation is to simply publish a Spring event
     * to the ApplicationContext after the transaction has completed.
     * 
     * @param entity the newly-persisted Customer
     * @see CustomerPersistedEvent
     */
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
