
package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.Searchable;


/**
 *
 * @ hui
 */
public interface SkuAttribute extends Searchable<String>, MultiTenantCloneable<SkuAttribute> {

    public Long getId();

    public void setId(Long id);

    public Sku getSku();

    public void setSku(Sku sku);

    public String getName();

    public void setName(String name);

}
