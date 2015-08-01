
package com.wakacommerce.core.search.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 * 
 */
public interface RequiredFacet extends Serializable, MultiTenantCloneable<RequiredFacet> {

    Long getId();

    void setId(Long id);

    SearchFacet getRequiredFacet();

    void setRequiredFacet(SearchFacet requiredFacet);

    SearchFacet getSearchFacet();

    void setSearchFacet(SearchFacet searchFacet);

}
