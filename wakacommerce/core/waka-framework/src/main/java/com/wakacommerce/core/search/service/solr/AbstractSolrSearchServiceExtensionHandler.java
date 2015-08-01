
package com.wakacommerce.core.search.service.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrInputDocument;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
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
 * Implementors of the SolrSearchServiceExtensionHandler interface should extend this class so that if 
 * additional extension points are added which they don't care about, their code will not need to be
 * modified.
 * 
 * 
 */                                      
public abstract class AbstractSolrSearchServiceExtensionHandler extends AbstractExtensionHandler
        implements SolrSearchServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType buildPrefixListForSearchableFacet(Field field, List<String> prefixList) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType buildPrefixListForSearchableField(Field field, FieldType searchableFieldType, List<String> prefixList) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType filterSearchFacetRanges(SearchFacetDTO dto, List<SearchFacetRange> ranges) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType addPropertyValues(Product product, Field field, FieldType fieldType,
            Map<String, Object> values, String propertyName, List<Locale> locales) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType addPropertyValues(Sku sku, Field field, FieldType fieldType,
            Map<String, Object> values, String propertyName, List<Locale> locales) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType modifySolrQuery(SolrQuery query, String qualifiedSolrQuery, List<SearchFacetDTO> facets, SearchCriteria searchCriteria, String defaultSort) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType attachAdditionalBasicFields(Product product, SolrInputDocument document, SolrHelperService shs) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType attachAdditionalBasicFields(Sku sku, SolrInputDocument document, SolrHelperService shs) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getSolrDocumentId(SolrInputDocument document, Product product, String[] returnContainer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getSolrDocumentId(SolrInputDocument document, Sku product, String[] returnContainer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getCategoryId(Category category, Long[] returnContainer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getCategoryId(Long category, Long[] returnContainer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getProductId(Product product, Long[] returnContainer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType getSkuId(Sku sku, Long[] returnContainer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    
    @Override
    public ExtensionResultStatusType modifyBuiltDocuments(Collection<SolrInputDocument> documents, List<Product> products, List<Field> fields, List<Locale> locales) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
