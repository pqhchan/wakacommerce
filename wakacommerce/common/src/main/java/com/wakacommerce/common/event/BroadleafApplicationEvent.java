
package com.wakacommerce.common.event;

import org.springframework.context.ApplicationEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Base abstract ApplicationEvent that provides a marker for Broadleaf events and provides a default 
 * context map. 
 * 
 * @see <code>com.wakacommerce.common.event.BroadleafApplicationEventMultiCaster</code>
 * @see <code>com.wakacommerce.common.event.BroadleafApplicationListener</code>
 * 
 *  
 *
 */
public abstract class BroadleafApplicationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	protected transient final Map<String, Object> context = Collections.synchronizedMap(new HashMap<String, Object>());
	
	/**
	 * Instantiates this with the required source. The asynchronous property is false and the errorHandler is null.
	 * @param source
	 */
	public BroadleafApplicationEvent(Object source) {
		super(source);
	}
	
	/**
	 * Context map that allows generic objects / properties to be passed around on events. This map is synchronized.
	 * @return
	 */
	public Map<String, Object> getConext() {
		return context;
	}
}
