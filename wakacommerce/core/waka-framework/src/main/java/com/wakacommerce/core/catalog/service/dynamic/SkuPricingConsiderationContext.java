
package com.wakacommerce.core.catalog.service.dynamic;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;
import com.wakacommerce.core.catalog.domain.SkuImpl;

import java.util.HashMap;

/**
 * Convenient place to store the pricing considerations context and the pricing service on thread local. This class is
 * usually filled out by a {@link com.wakacommerce.core.web.catalog.DynamicSkuPricingFilter}. The default
 * implementation of this is {@link com.wakacommerce.core.web.catalog.DefaultDynamicSkuPricingFilter}.
 * 
 *jfischer
 * @see {@link SkuImpl#getRetailPrice}
 * @see {@link SkuImpl#getSalePrice}
 * @see {@link com.wakacommerce.core.web.catalog.DynamicSkuPricingFilter}
 */
public class SkuPricingConsiderationContext {

    private static final ThreadLocal<SkuPricingConsiderationContext> skuPricingConsiderationContext = ThreadLocalManager.createThreadLocal(SkuPricingConsiderationContext.class);

    public static HashMap getSkuPricingConsiderationContext() {
        return SkuPricingConsiderationContext.skuPricingConsiderationContext.get().considerations;
    }
    
    public static void setSkuPricingConsiderationContext(HashMap skuPricingConsiderations) {
        SkuPricingConsiderationContext.skuPricingConsiderationContext.get().considerations = skuPricingConsiderations;
    }

    public static DynamicSkuPricingService getSkuPricingService() {
        return SkuPricingConsiderationContext.skuPricingConsiderationContext.get().pricingService;
    }
    
    public static void setSkuPricingService(DynamicSkuPricingService skuPricingService) {
        SkuPricingConsiderationContext.skuPricingConsiderationContext.get().pricingService = skuPricingService;
    }
    
    public static boolean hasDynamicPricing() {
        return (
            getSkuPricingConsiderationContext() != null &&
            getSkuPricingConsiderationContext().size() >= 0 &&
            getSkuPricingService() != null
        );
    }

    protected DynamicSkuPricingService pricingService;
    protected HashMap considerations;
}
