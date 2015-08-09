
package com.wakacommerce.core.web.catalog;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPricingService;
import com.wakacommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 *
 * @ hui
 */
public abstract class DynamicSkuPricingInterceptor implements WebRequestInterceptor {

    @Resource(name = "blDynamicSkuPricingService")
    protected DynamicSkuPricingService skuPricingService;

    @Override
    public void preHandle(WebRequest request) throws Exception {
        SkuPricingConsiderationContext.setSkuPricingConsiderationContext(getPricingConsiderations(request));
        SkuPricingConsiderationContext.setSkuPricingService(getDynamicSkuPricingService(request));
    }

    public DynamicSkuPricingService getDynamicSkuPricingService(WebRequest request) {
        return skuPricingService;
    }

    @SuppressWarnings("rawtypes")
    public abstract HashMap getPricingConsiderations(WebRequest request);

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {
        // unimplemented
    }

}
