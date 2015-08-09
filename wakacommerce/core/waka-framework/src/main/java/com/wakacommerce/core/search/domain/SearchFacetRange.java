
package com.wakacommerce.core.search.domain;

import java.math.BigDecimal;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 *
 * @ hui
 */
public interface SearchFacetRange extends MultiTenantCloneable<SearchFacetRange> {

    public Long getId();

    public void setId(Long id);

    public BigDecimal getMinValue();

    public void setMinValue(BigDecimal minValue);

    public BigDecimal getMaxValue();

    public void setMaxValue(BigDecimal maxValue);

    public SearchFacet getSearchFacet();

    public void setSearchFacet(SearchFacet searchFacet);

}
