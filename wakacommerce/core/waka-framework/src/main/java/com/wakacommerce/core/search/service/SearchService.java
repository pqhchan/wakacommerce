
package com.wakacommerce.core.search.service;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchResult;
import com.wakacommerce.core.search.service.solr.SolrIndexService;

import java.io.IOException;
import java.util.List;

/**
 * 
 * 
 */
public interface SearchService {

    /**
     * This method delegates to {@link SolrIndexService#rebuildIndex()}. It is here to preserve backwards-compatibility
     * with sites that were originally configured to run Broadleaf with Solr before 2.2.0.
     * 
     * @throws ServiceException
     * @throws IOException
     */
    public void rebuildIndex() throws ServiceException, IOException;
    
    /**
     * Performs a search for search results in the given category, taking into consideration the SearchCriteria
     * 
     * This method will return search results that are in any sub-level of a given category. For example, if you had a 
     * "Routers" category and a "Enterprise Routers" sub-category, asking for search results in "Routers", would return
     * search results that are in the "Enterprise Routers" category. 
     * 
     * @see #findExplicitSearchResultsByCategory(Category, SearchCriteria)
     * 
     * @param category
     * @param searchCriteria
     * @return the result of the search
     * @throws ServiceException 
     */
    public SearchResult findSearchResultsByCategory(Category category, SearchCriteria searchCriteria)
            throws ServiceException;
    
    /**
     * Performs a search for search results in the given category, taking into consideration the SearchCriteria
     * 
     * This method will NOT return search results that are in a sub-level of a given category. For example, if you had a 
     * "Routers" category and a "Enterprise Routers" sub-category, asking for search results in "Routers", would NOT return
     * search results that are in the "Enterprise Routers" category. 
     * 
     * @see #findSearchResultsByCategory(Category, SearchCriteria)
     * 
     * @param category
     * @param searchCriteria
     * @return
     * @throws ServiceException
     */
    public SearchResult findExplicitSearchResultsByCategory(Category category, SearchCriteria searchCriteria)
            throws ServiceException;
    
    /**
     * Performs a search for search results across all categories for the given query, taking into consideration
     * the SearchCriteria
     * 
     * @param query
     * @param searchCriteria
     * @return the result of the search
     * @throws ServiceException 
     */
    public SearchResult findSearchResultsByQuery(String query, SearchCriteria searchCriteria)
            throws ServiceException;
    
    /**
     * Performs a search for search results in the given category for the given query, taking into consideration 
     * the SearchCriteria
     * 
     * @param category
     * @param query
     * @param searchCriteria
     * @throws ServiceException
     */
    public SearchResult findSearchResultsByCategoryAndQuery(Category category, String query, SearchCriteria searchCriteria) throws ServiceException;

    /**
     * Gets all available facets for search results page
     * 
     * @return the available facets
     */
    public List<SearchFacetDTO> getSearchFacets();

    /**
     * Gets all available facets for a given category
     * 
     * @param category
     * @return the available facets
     */
    public List<SearchFacetDTO> getCategoryFacets(Category category);

}
