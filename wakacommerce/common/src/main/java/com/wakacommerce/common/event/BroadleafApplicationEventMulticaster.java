
package com.wakacommerce.common.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executor;

/**
 *
 * @ hui
 */
public class BroadleafApplicationEventMulticaster extends
        SimpleApplicationEventMulticaster implements ApplicationContextAware {
	
    @Autowired(required = false)
    @Qualifier("blApplicationEventMulticastTaskExecutor")
    private Executor taskExecutor;

	protected ApplicationContext ctx;

	@Override
	public void multicastEvent(final ApplicationEvent event) {
        Executor executor = getTaskExecutor();
        for (final ApplicationListener<?> listener : getApplicationListeners(event)) {
			boolean isAsynchronous = false;
			if (executor != null) {
                if ((BroadleafApplicationListener.class.isAssignableFrom(listener.getClass())
                            && ((BroadleafApplicationListener<? extends ApplicationEvent>)listener).isAsynchronous())) {
                    isAsynchronous = true;
			    }
			}
			
            if (isAsynchronous) {
				executor.execute(new Runnable() {
					public void run() {
                        invokeListener(listener, event);
					}
				});
			} else {
				invokeListener(listener, event);
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx = applicationContext;
	}
	
    public Executor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

}
