
package com.wakacommerce.core.search.service.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.sandbox.domain.SandBoxType;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.search.domain.Field;
import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.search.domain.SearchFacet;
import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchFacetRange;
import com.wakacommerce.core.search.domain.solr.FieldType;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface SolrHelperService {

    public void swapActiveCores() throws ServiceException;

    public String getCurrentNamespace();

    public String getGlobalFacetTagField();

    public String getPropertyNameForFieldSearchable(Field field, FieldType searchableFieldType, String prefix);

    public String getPropertyNameForFieldFacet(Field field, String prefix);

    public List<FieldType> getSearchableFieldTypes(Field field);

    public String getPropertyNameForFieldSearchable(Field field, FieldType searchableFieldType);

    public String getPropertyNameForFieldFacet(Field field);

    public String getSolrDocumentId(SolrInputDocument document, Product product);

    public String getSolrDocumentId(SolrInputDocument document, Sku sku);

    public String getNamespaceFieldName();

    public String getIdFieldName();

    public String getProductIdFieldName();

    public String getSkuIdFieldName();

    public String getCategoryFieldName();

    public String getExplicitCategoryFieldName();

    public String getCatalogFieldName();

    public String getCatalogOverridesFieldName();

    public String getSandBoxFieldName();

    public String getSandBoxPriorityFieldName();

    public String getSandBoxChangeTypeFieldName();

    public String getCategorySortFieldName(Category category);

    public String getCategorySortFieldName(Long categoryId);

    public String getLocalePrefix();

    public String getDefaultLocalePrefix();

    public Locale getDefaultLocale();

    public Long getCategoryId(Category category);

    public Long getCategoryId(Long category);

    public Long getProductId(Product product);

    public Long getSkuId(Sku sku);

    public Object getPropertyValue(Object object, Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    public Object getPropertyValue(Object object, String propertyName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    public void optimizeIndex(SolrServer server) throws ServiceException, IOException;

    public String scrubFacetValue(String facetValue);

    public String sanitizeQuery(String query);

    public List<SearchFacetDTO> buildSearchFacetDTOs(List<SearchFacet> searchFacets);

    public boolean isFacetAvailable(SearchFacet facet, Map<String, String[]> params);

    public String getSolrRangeString(String fieldName, BigDecimal minValue, BigDecimal maxValue);

    public String getSolrRangeFunctionString(BigDecimal minValue, BigDecimal maxValue);

    public String getSolrFieldTag(String tagField, String tag, SearchFacetRange range);

    public void setFacetResults(Map<String, SearchFacetDTO> namedFacetMap, QueryResponse response);

    public void sortFacetResults(Map<String, SearchFacetDTO> namedFacetMap);

    public void attachFacets(SolrQuery query, Map<String, SearchFacetDTO> namedFacetMap);

    public String getSolrTaggedFieldString(String indexField, String tag, SearchFacetRange range);

    public List<SolrDocument> getResponseDocuments(QueryResponse response);

    public void attachSortClause(SolrQuery query, SearchCriteria searchCriteria, String defaultSort, List<Field> fields);

    public Map<String, String> getSolrFieldKeyMap(SearchCriteria searchCriteria, List<Field> fields);

    public Map<String, SearchFacetDTO> getNamedFacetMap(List<SearchFacetDTO> facets, SearchCriteria searchCriteria);

    public void attachActiveFacetFilters(SolrQuery query, Map<String, SearchFacetDTO> namedFacetMap, SearchCriteria searchCriteria);
}
