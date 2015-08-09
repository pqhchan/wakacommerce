
package com.wakacommerce.core.catalog.service.dynamic;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;
import com.wakacommerce.core.catalog.domain.SkuImpl;

import java.util.HashMap;

/**
 *
 * @ hui
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
