
package com.wakacommerce.common.vendor.service.cache;

import net.sf.ehcache.Cache;


/**
 *
 * @ hui
 */
public interface ServiceResponseCacheable {
    
    public void clearCache();
    
    public Cache getCache();

}
