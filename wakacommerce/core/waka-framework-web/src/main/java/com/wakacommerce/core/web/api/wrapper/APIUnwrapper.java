
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public interface APIUnwrapper<T> {
    public T unwrap(HttpServletRequest request, ApplicationContext context);
}
