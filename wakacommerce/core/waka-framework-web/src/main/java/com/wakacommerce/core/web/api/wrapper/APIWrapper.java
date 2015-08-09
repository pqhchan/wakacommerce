
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public interface APIWrapper<T> {

    public void wrapDetails(T model, HttpServletRequest request);

    public void wrapSummary(T model, HttpServletRequest request);

}
