
package com.wakacommerce.core.catalog.service.dynamic;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.ProductOptionValueImpl;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuBundleItem;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 *
 * @ hui
 */
public interface DynamicSkuPricingService {

    @Nonnull
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuPrices(Sku sku, HashMap skuPricingConsiderations);

    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuBundleItemPrice(SkuBundleItem sku, HashMap skuPricingConsiderations);

    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getPriceAdjustment(ProductOptionValueImpl productOptionValueImpl, Money priceAdjustment,
            HashMap skuPricingConsiderationContext);

}
