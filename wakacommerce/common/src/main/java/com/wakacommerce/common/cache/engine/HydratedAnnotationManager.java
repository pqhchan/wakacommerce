
package com.wakacommerce.common.cache.engine;

/**
 * 
 *jfischer
 *
 */
public interface HydratedAnnotationManager {

    public HydrationDescriptor getHydrationDescriptor(Object entity);
    
}
