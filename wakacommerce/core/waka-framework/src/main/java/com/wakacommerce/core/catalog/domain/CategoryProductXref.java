package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 * @see {@link CategoryProductXrefImpl}, {@link Category}, {@link Product}
 * 
 * @author hui
 */
public interface CategoryProductXref extends Serializable, MultiTenantCloneable<CategoryProductXref> {

    Category getCategory();

    void setCategory(Category category);

    Product getProduct();

    void setProduct(Product product);

    BigDecimal getDisplayOrder();

    void setDisplayOrder(BigDecimal displayOrder);

    void setId(Long id);

    Long getId();
    
    Boolean getDefaultReference();

    void setDefaultReference(Boolean defaultReference);

}
