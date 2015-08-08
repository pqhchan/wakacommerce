  
package com.wakacommerce.core.web.service;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

/**
 * Used to build a cacheKey for caching templates.
 *   ( )
 */
public interface TemplateCacheKeyResolverService {

    /**
     * Takes in the Thymeleaf arguments, element, and templateName.    Returns the cacheKey by which
     * this template can be cached.      
     * 
     * @see SimpleCacheKeyResolver
     * 
     * @param arguments
     * @param element
     * @return
     */
    public String resolveCacheKey(Arguments arguments, Element element);
}
