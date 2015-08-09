
package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.Searchable;


/**
 *
 * @ hui
 */
public interface CategoryAttribute extends Searchable<String>, MultiTenantCloneable<CategoryAttribute> {

    public Long getId();

    public void setId(Long id);

    public String getValue();

    public void setValue(String value);

    public Category getCategory();

    public void setCategory(Category category);

    public String getName();

    public void setName(String name);

}
