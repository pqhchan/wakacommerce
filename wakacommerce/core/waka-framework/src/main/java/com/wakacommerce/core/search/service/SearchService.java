
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
 * @ hui
 */
public interface SearchService {

    public void rebuildIndex() throws ServiceException, IOException;

    public SearchResult findSearchResultsByCategory(Category category, SearchCriteria searchCriteria)
            throws ServiceException;

    public SearchResult findExplicitSearchResultsByCategory(Category category, SearchCriteria searchCriteria)
            throws ServiceException;

    public SearchResult findSearchResultsByQuery(String query, SearchCriteria searchCriteria)
            throws ServiceException;

    public SearchResult findSearchResultsByCategoryAndQuery(Category category, String query, SearchCriteria searchCriteria) throws ServiceException;

    public List<SearchFacetDTO> getSearchFacets();

    public List<SearchFacetDTO> getCategoryFacets(Category category);

}
