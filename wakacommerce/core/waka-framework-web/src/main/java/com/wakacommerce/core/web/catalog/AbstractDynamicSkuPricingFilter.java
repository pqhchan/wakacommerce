
package com.wakacommerce.core.web.catalog;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.wakacommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

import java.io.IOException;

/**
 *
 * @ hui
 */
public abstract class AbstractDynamicSkuPricingFilter implements DynamicSkuPricingFilter {

    public void destroy() {
        //do nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        SkuPricingConsiderationContext.setSkuPricingConsiderationContext(getPricingConsiderations(request));
        SkuPricingConsiderationContext.setSkuPricingService(getDynamicSkuPricingService(request));
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        //do nothing
    }

}
