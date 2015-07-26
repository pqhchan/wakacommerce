
package com.wakacommerce.core.catalog.service.dynamic;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.domain.ProductOptionValueImpl;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.domain.SkuBundleItem;

import java.util.HashMap;

/**
 * Default implementation of the {@link DynamicSkuPricingService} which simply ignores the considerations hashmap in all
 * method implementations
 * 
 *jfischer
 * 
 */
@Service("blDynamicSkuPricingService")
public class DefaultDynamicSkuPricingServiceImpl implements DynamicSkuPricingService {

    @Override
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuPrices(Sku sku, HashMap skuPricingConsiderations) {
        DynamicSkuPrices prices = new DynamicSkuPrices();
        prices.setRetailPrice(sku.getRetailPrice());
        prices.setSalePrice(sku.getSalePrice());
        prices.setPriceAdjustment(sku.getProductOptionValueAdjustments());
        return prices;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getSkuBundleItemPrice(SkuBundleItem skuBundleItem,
            HashMap skuPricingConsiderations) {
        DynamicSkuPrices prices = new DynamicSkuPrices();
        prices.setSalePrice(skuBundleItem.getSalePrice());
        return prices;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public DynamicSkuPrices getPriceAdjustment(ProductOptionValueImpl productOptionValueImpl, Money priceAdjustment,
            HashMap skuPricingConsiderationContext) {
        DynamicSkuPrices prices = new DynamicSkuPrices();

        prices.setPriceAdjustment(priceAdjustment);
        return prices;
    }

}
