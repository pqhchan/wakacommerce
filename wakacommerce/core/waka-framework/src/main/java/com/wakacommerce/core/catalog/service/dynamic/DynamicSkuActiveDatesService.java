
package com.wakacommerce.core.catalog.service.dynamic;

import java.util.Date;

import javax.annotation.Nonnull;

import com.wakacommerce.core.catalog.domain.Sku;

/**
 * <p>Interface for dynamically determining the activity dates.</p>
 * 
 * Provides an ability to set active dates programatically.   Intended for use by add-on modules like 
 * the PriceList module which supports activeDates dates by PriceList.   
 * 
 * Even if the dates are being overridden dynamically, the master activeStart and activeEnd dates still
 * control the global activeDates of a SKU.    
 * 
 * <p>Rather than implementing this interface directly, consider sub-classing the {@link DefaultDynamicSkuActiveDatesServiceImpl}
 * and providing overrides to methods there.</p>
 * 
 * 
 *
 */
public interface DynamicSkuActiveDatesService {

    /**
     * Returns the activeStartDate for the SKU if it has been overridden.
     * 
     * @param sku
     * @return
     */
    @Nonnull
    @SuppressWarnings("rawtypes")
    public Date getDynamicSkuActiveStartDate(Sku sku);

    /**
     * Returns the activeEndDate for the SKU if it has been overridden.
     * 
     * @param sku
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Date getDynamicSkuActiveEndDate(Sku sku);
}
