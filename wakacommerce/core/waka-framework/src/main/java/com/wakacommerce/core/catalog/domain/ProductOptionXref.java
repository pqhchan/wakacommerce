
package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 *
 * @ hui
 */
public interface ProductOptionXref extends Serializable, MultiTenantCloneable<ProductOptionXref> {

    Long getId();

    void setId(Long id);

    Product getProduct();

    void setProduct(Product product);

    ProductOption getProductOption();

    void setProductOption(ProductOption productOption);

}
