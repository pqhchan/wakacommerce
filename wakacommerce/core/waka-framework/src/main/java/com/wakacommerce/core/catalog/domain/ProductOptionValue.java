
package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.money.Money;

/**
 *
 * @ hui
 */
public interface ProductOptionValue extends Serializable, MultiTenantCloneable<ProductOptionValue> {

    public Long getId();

    public void setId(Long id);

    public String getAttributeValue();

    public void setAttributeValue(String attributeValue);

    public Long getDisplayOrder();

    public void setDisplayOrder(Long order);

    public Money getPriceAdjustment();

    public void setPriceAdjustment(Money priceAdjustment);

    public ProductOption getProductOption();

    public void setProductOption(ProductOption productOption);
    
}
