
package com.wakacommerce.core.web.catalog;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPricingService;
import com.wakacommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

import java.util.HashMap;

/**
 *
 * @ hui
 */
public interface DynamicSkuPricingFilter extends Filter {

    @SuppressWarnings("rawtypes")
    public HashMap getPricingConsiderations(ServletRequest request);

    public DynamicSkuPricingService getDynamicSkuPricingService(ServletRequest request);
    
}
