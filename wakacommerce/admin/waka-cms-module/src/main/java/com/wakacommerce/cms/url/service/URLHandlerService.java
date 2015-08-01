 
package com.wakacommerce.cms.url.service;

import net.sf.ehcache.Cache;

import java.util.List;

import com.wakacommerce.cms.url.domain.URLHandler;
import com.wakacommerce.common.sandbox.domain.SandBox;


/**
 *   
 */
public interface URLHandlerService {

    /**
     * Checks the passed in URL to determine if there is a matching URLHandler.
     * Returns null if no handler was found.
     * 
     * @param uri
     * @return
     */
    URLHandler findURLHandlerByURI(String uri);
    
    List<URLHandler> findAllURLHandlers();
    
    URLHandler saveURLHandler(URLHandler handler);

    URLHandler findURLHandlerById(Long id);

}
