
package com.wakacommerce.common.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 *
 * @ hui
 */
public interface BroadleafApplicationListener<E extends ApplicationEvent> extends ApplicationListener<E> {

    public boolean isAsynchronous();

}
