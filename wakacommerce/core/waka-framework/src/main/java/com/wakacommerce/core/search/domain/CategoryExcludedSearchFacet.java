
package com.wakacommerce.core.search.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.core.catalog.domain.Category;

import java.math.BigDecimal;

/**
 *
 * @ hui
 */
public interface CategoryExcludedSearchFacet extends MultiTenantCloneable<CategoryExcludedSearchFacet>{

    public Long getId();

    public void setId(Long id);

    public Category getCategory();

    public void setCategory(Category category);

    public SearchFacet getSearchFacet();

    public void setSearchFacet(SearchFacet searchFacet);

    public BigDecimal getSequence();

    public void setSequence(BigDecimal sequence);

}
