
package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wakacommerce.common.copy.MultiTenantCloneable;


/**
 * Implementations of this interface are used to hold data about the many-to-many relationship between
 * the Category table and a parent Category.
 * </p>
 * You should implement this class if you want to make significant changes to the
 * relationship between Category and parent Category.  If you just want to add additional fields
 * then you should extend {@link CategoryXrefImpl}.
 *
 *  @see {@link CategoryXrefImpl},{@link Category}
 *
 */
public interface CategoryXref extends Serializable, MultiTenantCloneable<CategoryXref> {

    /**
     * Return the order for sorting
     *
     * @return
     */
    public BigDecimal getDisplayOrder();

    public void setDisplayOrder(final BigDecimal displayOrder);

    /**
     * Return the parent category
     *
     * @return
     */
    public Category getCategory();

    public void setCategory(final Category category);

    /**
     * Return the child category
     *
     * @return
     */
    public Category getSubCategory();

    public void setSubCategory(final Category subCategory);

    /**
     * Return the primary key
     *
     * @param id
     */
    public void setId(Long id);

    public Long getId();

    /**
     * Specifies the default reference between a category and a parent category. This replaces the concept of
     * {@link com.wakacommerce.core.catalog.domain.CategoryImpl#getDefaultParentCategory()} ()}
     *
     * @see com.wakacommerce.core.catalog.domain.CategoryImpl#getParentCategory() ()
     * @return the default reference between a category and a parent category
     */
    Boolean getDefaultReference();

    void setDefaultReference(Boolean defaultReference);
}
