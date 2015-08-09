
package com.wakacommerce.core.search.service.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrInputDocument;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.core.catalog.domain.Category;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.search.domain.Field;
import com.wakacommerce.core.search.domain.SearchCriteria;
import com.wakacommerce.core.search.domain.SearchFacetDTO;
import com.wakacommerce.core.search.domain.SearchFacetRange;
import com.wakacommerce.core.search.domain.solr.FieldType;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 *
 * @ hui
 */                                      
public interface SolrSearchServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType buildPrefixListForSearchableFacet(Field field, List<String> prefixList);

    public ExtensionResultStatusType buildPrefixListForSearchableField(Field field, FieldType searchableFieldType,
            List<String> prefixList);

    public ExtensionResultStatusType filterSearchFacetRanges(SearchFacetDTO dto, List<SearchFacetRange> ranges);

    public ExtensionResultStatusType addPropertyValues(Product product, Field field, FieldType fieldType,
            Map<String, Object> values, String propertyName, List<Locale> locales)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    public ExtensionResultStatusType addPropertyValues(Sku sku, Field field, FieldType fieldType,
            Map<String, Object> values, String propertyName, List<Locale> locales)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    public ExtensionResultStatusType modifySolrQuery(SolrQuery query, String qualifiedSolrQuery,
            List<SearchFacetDTO> facets, SearchCriteria searchCriteria, String defaultSort);

    public ExtensionResultStatusType attachAdditionalBasicFields(Product product, SolrInputDocument document, SolrHelperService shs);

    public ExtensionResultStatusType attachAdditionalBasicFields(Sku sku, SolrInputDocument document, SolrHelperService shs);

    public ExtensionResultStatusType getSolrDocumentId(SolrInputDocument document, Product product, String[] returnContainer);

    public ExtensionResultStatusType getSolrDocumentId(SolrInputDocument document, Sku sku, String[] returnContainer);

    public ExtensionResultStatusType getCategoryId(Category category, Long[] returnContainer);

    public ExtensionResultStatusType getCategoryId(Long category, Long[] returnContainer);

    public ExtensionResultStatusType getProductId(Product product, Long[] returnContainer);

    public ExtensionResultStatusType getSkuId(Sku sku, Long[] returnContainer);
    
    public ExtensionResultStatusType modifyBuiltDocuments(Collection<SolrInputDocument> documents, List<Product> products, List<Field> fields, List<Locale> locales);

}
