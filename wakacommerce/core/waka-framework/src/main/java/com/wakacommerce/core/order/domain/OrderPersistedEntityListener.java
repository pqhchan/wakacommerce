
package com.wakacommerce.core.order.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.wakacommerce.common.util.ApplicationContextHolder;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;


/**
 * The main function of this entity listener is to publish a Spring event that the customer has been persisted. This is
 * necessary in order to update the current order in the application
 *
 *     
 * 
 * @see {@link ApplicationEventPublisher#publishEvent(org.springframework.context.ApplicationEvent)}
 * @see {@link OrderPersistedEvent}
 * @see {@link com.wakacommerce.core.web.order.CartStateRefresher}
 * @see {@link com.wakacommerce.core.web.order.CartState}
 */
public class OrderPersistedEntityListener {

    /**
     * Invoked on both the PostPersist and PostUpdate. The default implementation is to simply publish a Spring event
     * to the ApplicationContext to allow an event listener to respond appropriately (like resetting the current cart
     * in CartState)
     * 
     * @param entity the newly-persisted Order
     * @see OrderPersistedEvent
     */
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
