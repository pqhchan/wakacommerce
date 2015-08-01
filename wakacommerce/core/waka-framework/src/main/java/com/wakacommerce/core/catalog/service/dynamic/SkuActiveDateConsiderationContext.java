
package com.wakacommerce.core.catalog.service.dynamic;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;
import com.wakacommerce.core.catalog.domain.SkuImpl;

import java.util.HashMap;

/**
 * Convenient place to store the active date context and the related service on thread local. 
 * 
 *  
 * @see {@link SkuImpl#getActiveStartDate()}
 * @see {@link SkuImpl#getActiveEndDate()}
 */
public class SkuActiveDateConsiderationContext {

    private static final ThreadLocal<SkuActiveDateConsiderationContext> skuActiveDatesConsiderationContext =
            ThreadLocalManager.createThreadLocal(SkuActiveDateConsiderationContext.class);

    public static HashMap getSkuActiveDateConsiderationContext() {
        return SkuActiveDateConsiderationContext.skuActiveDatesConsiderationContext.get().considerations;
    }

    public static void setSkuActiveDateConsiderationContext(HashMap skuPricingConsiderations) {
        SkuActiveDateConsiderationContext.skuActiveDatesConsiderationContext.get().considerations = skuPricingConsiderations;
    }

    public static DynamicSkuActiveDatesService getSkuActiveDatesService() {
        return SkuActiveDateConsiderationContext.skuActiveDatesConsiderationContext.get().service;
    }

    public static void setSkuActiveDatesService(DynamicSkuActiveDatesService skuPricingService) {
        SkuActiveDateConsiderationContext.skuActiveDatesConsiderationContext.get().service = skuPricingService;
    }

    public static boolean hasDynamicActiveDates() {
        return (getSkuActiveDatesService() != null);
    }

    protected DynamicSkuActiveDatesService service;
    protected HashMap considerations;

}
