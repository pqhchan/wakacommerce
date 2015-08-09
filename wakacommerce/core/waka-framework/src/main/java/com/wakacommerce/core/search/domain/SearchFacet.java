
package com.wakacommerce.core.search.domain;

import java.util.List;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 *
 * @ hui
 */
public interface SearchFacet extends MultiTenantCloneable<SearchFacet> {

    public Long getId();

    public void setId(Long id);

    public Field getField();

    public void setField(Field field);

    public String getLabel();

    public void setLabel(String label);

    public Boolean getShowOnSearch();

    public void setShowOnSearch(Boolean showOnSearch);

    public Integer getSearchDisplayPriority();

    public void setSearchDisplayPriority(Integer searchDisplayPriority);

    public void setCanMultiselect(Boolean canMultiselect);

    public Boolean getCanMultiselect();

    public List<SearchFacetRange> getSearchFacetRanges();

    public void setSearchFacetRanges(List<SearchFacetRange> searchFacetRanges);

    public List<RequiredFacet> getRequiredFacets();

    public void setRequiredFacets(List<RequiredFacet> requiredFacets);

    public Boolean getRequiresAllDependentFacets();

    public void setRequiresAllDependentFacets(Boolean requiresAllDependentFacets);

}
