
package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.money.Money;

/**
 *
 * @ hui
 */
public interface SkuBundleItem extends Serializable, MultiTenantCloneable<SkuBundleItem> {

    public Long getId();

    public void setId(Long id);

    public Integer getQuantity();

    public void setQuantity(Integer quantity);

    public void setSalePrice(Money salePrice);

    public Money getSalePrice();

    public ProductBundle getBundle();

    public void setBundle(ProductBundle bundle);

    public Money getRetailPrice();

    public Sku getSku();

    public void setSku(Sku sku);

    public void clearDynamicPrices();
}
