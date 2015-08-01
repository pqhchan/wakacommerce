
package com.wakacommerce.common.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * Works in conjection with <code>com.wakacommerce.common.event.BroadleafApplicationEventMulticaster</code> except 
 * the event listener can indicate if the event should be run in a background thread.  If no TaskExecutor is 
 * configured on the BroadleafApplicationEventMulticaster then it will be executed synchronously, regardless of 
 * of whether an event listener is configured.
 * 
 *  
 *
 * @param <ApplicationEvent>
 */
public interface BroadleafApplicationListener<E extends ApplicationEvent> extends ApplicationListener<E> {

    /**
     * Indicates if this application listener should be run in a background thread if a TaskExecutor is configured. 
     * If no TaskExecutor is configure with a bean name of "blApplicationEventMulticastTaskExecutor" then this will 
     * be run synchronously regardless. Generally, this should return false as the default implementation has no 
     * reliability guarantees associated with Asynch processing.  
     * However, for convenience this allows asynch processing for situations that don't require 
     * guaranteed processing.  For example, publishing statistics or log events in a background thread would be 
     * candidates for background processing. Handling important database updates would not.
     * 
     * @see com.wakacommerce.common.event.BroadleafApplicationEventMulticaster
     * 
     * @return
     */
    public boolean isAsynchronous();

}
