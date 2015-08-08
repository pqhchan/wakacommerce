  
package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 * Join entity between {@link Sku} and {@link ProductOptionValue}.
 * 
 * 
 */
public interface SkuProductOptionValueXref extends Serializable, MultiTenantCloneable<SkuProductOptionValueXref> {

    public Long getId();

    public void setId(Long id);

    public Sku getSku();

    public void setSku(Sku sku);

    public ProductOptionValue getProductOptionValue();

    public void setProductOptionValue(ProductOptionValue productOptionValue);

}