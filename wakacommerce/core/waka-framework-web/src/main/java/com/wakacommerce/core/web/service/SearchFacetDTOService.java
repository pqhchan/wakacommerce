
package com.wakacommerce.core.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchFacetResultDTO;

/**
 * Provides methods that facilitate interactions with SearchFacetDTOs and SearchFacetResultDTOs
 * 
 * 
 */
public interface SearchFacetDTOService {

    /**
     * Given a servlet request and a list of available facets for this request (could be search or category based),
     * this method will build out a SearchCriteria object to be used by the ProductSearchService. It will
     * perform translations from query string parameters to the SearchCriteria.
     * 
     * @param request
     * @param availableFacets
     * @return the SearchCriteria 
     */
    public SearchCriteria buildSearchCriteria(HttpServletRequest request, List<SearchFacetDTO> availableFacets);

    /**
     * Sets the "active" boolean on a given SearchFacetResultDTO as determined by the current request
     * 
     * @param facets
     * @param request
     */
    public void setActiveFacetResults(List<SearchFacetDTO> facets, HttpServletRequest request);

    /**
     * Returns whether or not the SearchFacetResultDTO's key/value pair is present in the servlet request
     * 
     * @param result
     * @param request
     * @return if the result is active
     */
    public boolean isActive(SearchFacetResultDTO result, HttpServletRequest request);
    
    /**
     * Gets the url abbreviation associated with a given SearchFacetResultDTO.
     * 
     * @param result
     * @return the key associated with a SearchFacetResultDTO
     */
    public String getUrlKey(SearchFacetResultDTO result);

    /**
     * Gets the value of the given SearchFacetResultDTO.
     * The default Broadleaf implementation will return the String value of the result if the value
     * is not empty, or "range[<min-value>:<max-value>]" if the value was empty.
     * 
     * @param result
     * @return the value of the SearchFacetResultDTO
     */
    public String getValue(SearchFacetResultDTO result);


}
