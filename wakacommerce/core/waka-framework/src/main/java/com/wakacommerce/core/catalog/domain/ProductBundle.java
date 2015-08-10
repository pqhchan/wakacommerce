package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.service.type.ProductBundlePricingModelType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @ hui
 */
public interface ProductBundle extends Product, Serializable {

    public ProductBundlePricingModelType getPricingModel();

    public void setPricingModel(ProductBundlePricingModelType pricingModel);

    public Money getRetailPrice();

    public Money getSalePrice();

    public Money getBundleItemsRetailPrice();

    public Money getBundleItemsSalePrice();

    public Boolean getAutoBundle();

    public void setAutoBundle(Boolean autoBundle);

    public Boolean getItemsPromotable();

    public void setItemsPromotable(Boolean itemsPromotable);

    public Boolean getBundlePromotable();

    public void setBundlePromotable(Boolean bundlePromotable);

    public List<SkuBundleItem> getSkuBundleItems();

    public void setSkuBundleItems(List<SkuBundleItem> bundleItems);

    public Integer getPriority();

    public void setPriority(Integer priority);

    public BigDecimal getPotentialSavings();

    public boolean isOnSale();

}
