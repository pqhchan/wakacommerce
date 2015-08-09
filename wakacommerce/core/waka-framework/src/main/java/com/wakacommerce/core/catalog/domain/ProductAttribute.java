
package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.Searchable;

/**
 *
 * @ hui
 */
public interface ProductAttribute extends Searchable<String>, MultiTenantCloneable<ProductAttribute> {

    Long getId();

    void setId(Long id);

    Product getProduct();

    void setProduct(Product product);

}
