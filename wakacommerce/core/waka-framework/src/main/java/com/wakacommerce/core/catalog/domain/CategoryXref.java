package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 * @see {@link CategoryXrefImpl}, {@link Category}
 * 
 * @author hui
 */
public interface CategoryXref extends Serializable, MultiTenantCloneable<CategoryXref> {

    public BigDecimal getDisplayOrder();

    public void setDisplayOrder(final BigDecimal displayOrder);

    public Category getCategory();

    public void setCategory(final Category category);

    public Category getSubCategory();

    public void setSubCategory(final Category subCategory);

    public void setId(Long id);

    public Long getId();

    Boolean getDefaultReference();

    void setDefaultReference(Boolean defaultReference);
}
