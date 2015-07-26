
package com.wakacommerce.core.search.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *Andre Azzolini (apazzolini)
 */
public class SearchFacetDTO {
    
    protected SearchFacet facet;
    protected boolean showQuantity;
    protected List<SearchFacetResultDTO> facetValues = new ArrayList<SearchFacetResultDTO>();
    protected boolean active;
    
    public SearchFacet getFacet() {
        return facet;
    }
    
    public void setFacet(SearchFacet facet) {
        this.facet = facet;
    }
    
    public boolean isShowQuantity() {
        return showQuantity;
    }
    
    public void setShowQuantity(boolean showQuantity) {
        this.showQuantity = showQuantity;
    }
    
    public List<SearchFacetResultDTO> getFacetValues() {
        return facetValues;
    }
    
    public void setFacetValues(List<SearchFacetResultDTO> facetValues) {
        this.facetValues = facetValues;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
