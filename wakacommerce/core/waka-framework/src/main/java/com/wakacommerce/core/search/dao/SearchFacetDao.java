
package com.wakacommerce.core.search.dao;

import java.util.List;

import com.wakacommerce.core.search.domain.FieldEntity;
import com.wakacommerce.core.search.domain.SearchFacet;

/**
 *
 * @ hui
 */
public interface SearchFacetDao {

    public <T> List<T> readDistinctValuesForField(String fieldName, Class<T> fieldValueClass);

    public List<SearchFacet> readAllSearchFacets(FieldEntity entityType);

    public SearchFacet save(SearchFacet searchFacet);
}
