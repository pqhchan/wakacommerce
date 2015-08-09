
package com.wakacommerce.core.web.service;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

/**
 *
 * @ hui
 */
public interface TemplateCacheKeyResolverService {

    public String resolveCacheKey(Arguments arguments, Element element);
}
