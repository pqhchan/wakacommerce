
package com.wakacommerce.common.vendor.service.cache;

import net.sf.ehcache.Cache;


/**
 *  
 *
 */
public interface ServiceResponseCacheable {
    
    public void clearCache();
    
    public Cache getCache();

}
