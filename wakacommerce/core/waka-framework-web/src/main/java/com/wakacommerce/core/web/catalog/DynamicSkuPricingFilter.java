
package com.wakacommerce.core.web.catalog;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPricingService;
import com.wakacommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

import java.util.HashMap;

/**
 * Responsible for setting up the {@link SkuPricingConsiderationContext}. Rather than simply creating a filter that
 * implements this interface, consider instead subclassing the {@link DefaultDynamicSkuPricingFilter} and overriding the
 * appropriate methods.
 * 
 *  
 * @see {@link DefaultDynamicSkuPricingFilter}
 * @see {@link AbstractDynamicSkuPricingFilter}
 * @see {@link DynamicSkuPricingService}
 * @see {@link SkuPricingConsiderationContext}
 */
public interface DynamicSkuPricingFilter extends Filter {

    /**
     * The result of this invocation should be set on
     * {@link SkuPricingConsiderationContext#setSkuPricingConsiderationContext(HashMap)} and ultimately passed to
     * {@link DynamicSkuPricingService} to determine prices.
     * 
     * @param request
     * @return a map of considerations to be used by the service in {@link #getDynamicSkuPricingService(ServletRequest)}.
     * @see {@link SkuPricingConsiderationContext#getSkuPricingConsiderationContext()}
     * @see {@link DynamicSkuPricingService}
     */
    @SuppressWarnings("rawtypes")
    public HashMap getPricingConsiderations(ServletRequest request);

    /**
     * The result of this invocation should be set on
     * {@link SkuPricingConsiderationContext#setSkuPricingService(DynamicSkuPricingService)}. This is the service that will
     * be used in calculating dynamic prices for a Sku or product option value
     * 
     * @param request
     * @return
     * @see {@link Sku#getRetailPrice()}
     * @see {@link Sku#getSalePrice()}
     */
    public DynamicSkuPricingService getDynamicSkuPricingService(ServletRequest request);
    
}
