
package com.wakacommerce.common.cache.engine;

/**
 *
 * @ hui
 */
public interface HydratedAnnotationManager {

    public HydrationDescriptor getHydrationDescriptor(Object entity);
    
}
