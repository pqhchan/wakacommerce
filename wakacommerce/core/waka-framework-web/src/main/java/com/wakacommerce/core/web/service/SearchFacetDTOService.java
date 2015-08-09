
package com.wakacommerce.core.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchFacetResultDTO;

/**
 *
 * @ hui
 */
public interface SearchFacetDTOService {

    public SearchCriteria buildSearchCriteria(HttpServletRequest request, List<SearchFacetDTO> availableFacets);

    public void setActiveFacetResults(List<SearchFacetDTO> facets, HttpServletRequest request);

    public boolean isActive(SearchFacetResultDTO result, HttpServletRequest request);

    public String getUrlKey(SearchFacetResultDTO result);

    public String getValue(SearchFacetResultDTO result);


}
