
package com.wakacommerce.core.catalog.service.dynamic;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.ProductOptionValueImpl;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuBundleItem;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * <p>Interface for calculating dynamic pricing for a {@link Sku}. This should be hooked up via a custom subclass of 
 * {@link com.wakacommerce.core.web.catalog.DefaultDynamicSkuPricingFilter} where an implementation of this class
 * should be injected and returned in the getPricing() method.</p>
 * 
 * <p>Rather than implementing this interface directly, consider subclassing the {@link DefaultDynamicSkuPricingServiceImpl}
 * and providing overrides to methods there.</p>
 * 
 *  
 * @see {@link DefaultDynamicSkuPricingServiceImpl}
 * @see {@link com.wakacommerce.core.web.catalog.DefaultDynamicSkuPricingFilter}
 * @see {@link SkuPricingConsiderationContext}
 */
public interface DynamicSkuPricingService {

    /**
     * While this method should return a {@link DynamicSkuPrices} (and not just null) the members of the result can all
     * be null; they do not have to be set
     * 
     * @param sku
     * @param skuPricingConsiderations
     * @return
     */
    @Nonnull
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuPrices(Sku sku, HashMap skuPricingConsiderations);

    /**
     * Used for t
     * 
     * @param sku
     * @param skuPricingConsiderations
     * @return
     */
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuBundleItemPrice(SkuBundleItem sku, HashMap skuPricingConsiderations);

    /**
     * Execute dynamic pricing on the price of a product option value. 
     * @param productOptionValueImpl
     * @param priceAdjustment
     * @param skuPricingConsiderationContext
     * @return
     */
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getPriceAdjustment(ProductOptionValueImpl productOptionValueImpl, Money priceAdjustment,
            HashMap skuPricingConsiderationContext);

}
