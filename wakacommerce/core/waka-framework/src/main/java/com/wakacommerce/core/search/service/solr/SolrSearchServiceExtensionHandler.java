
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
 *Andre Azzolini (apazzolini), bpolster
 */                                      
public interface SolrSearchServiceExtensionHandler extends ExtensionHandler {

    /**
     * Returns a prefix if required for the passed in facet.
     */
    public ExtensionResultStatusType buildPrefixListForSearchableFacet(Field field, List<String> prefixList);

    /**
     * Returns a prefix if required for the passed in searchable field. 
     */
    public ExtensionResultStatusType buildPrefixListForSearchableField(Field field, FieldType searchableFieldType,
            List<String> prefixList);

    /**
     * Builds the search facet ranges for the provided dto.
     * 
     * @param context
     * @param dto
     * @param ranges
     */
    public ExtensionResultStatusType filterSearchFacetRanges(SearchFacetDTO dto, List<SearchFacetRange> ranges);

    /**
     * Given the input field, populates the values array with the fields needed for the 
     * passed in field.   
     * 
     * For example, a handler might create multiple fields for the given passed in field.
     * @param product
     * @param field
     * @param values
     * @param propertyName
     * @param locales
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public ExtensionResultStatusType addPropertyValues(Product product, Field field, FieldType fieldType,
            Map<String, Object> values, String propertyName, List<Locale> locales)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * Given the input field, populates the values array with the fields needed for the 
     * passed in field.   
     * 
     * For example, a handler might create multiple fields for the given passed in field.
     * @param sku
     * @param field
     * @param values
     * @param propertyName
     * @param locales
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public ExtensionResultStatusType addPropertyValues(Sku sku, Field field, FieldType fieldType,
            Map<String, Object> values, String propertyName, List<Locale> locales)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * Provides an extension point to modify the SolrQuery.
     * 
     * @param context
     * @param query
     * @param qualifiedSolrQuery
     * @param facets
     * @param searchCriteria
     * @param defaultSort
     *      * @return
     */
    public ExtensionResultStatusType modifySolrQuery(SolrQuery query, String qualifiedSolrQuery,
            List<SearchFacetDTO> facets, SearchCriteria searchCriteria, String defaultSort);

    /**
     * Allows the extension additional fields to the document that are not configured via the DB.
     */
    public ExtensionResultStatusType attachAdditionalBasicFields(Product product, SolrInputDocument document, SolrHelperService shs);

    /**
     * Allows the extension additional fields to the document that are not configured via the DB.
     */
    public ExtensionResultStatusType attachAdditionalBasicFields(Sku sku, SolrInputDocument document, SolrHelperService shs);
    
    /**
     * In certain scenarios, we may want to produce a different Solr document id than the default.
     * If this method returns {@link ExtensionResultStatusType#HANDLED}, the value placed in the 0th element
     * in the returnContainer should be used.
     * 
     * @param document
     * @param product
     * @return the extension result status type
     */
    public ExtensionResultStatusType getSolrDocumentId(SolrInputDocument document, Product product, String[] returnContainer);

    /**
     * In certain scenarios, we may want to produce a different Solr document id than the default.
     * If this method returns {@link ExtensionResultStatusType#HANDLED}, the value placed in the 0th element
     * in the returnContainer should be used.
     * 
     * @param document
     * @param sku
     * @return the extension result status type
     */
    public ExtensionResultStatusType getSolrDocumentId(SolrInputDocument document, Sku sku, String[] returnContainer);

    /**
     * In certain scenarios, the requested category id might not be the one that should be used in Solr.
     * If this method returns {@link ExtensionResultStatusType#HANDLED}, the value placed in the 0th element
     * in the returnContainer should be used.
     * 
     * @param category
     * @param returnContainer
     * @return the extension result status type
     */
    public ExtensionResultStatusType getCategoryId(Category category, Long[] returnContainer);

    /**
     * In certain scenarios, the requested category id might not be the one that should be used in Solr.
     * If this method returns {@link ExtensionResultStatusType#HANDLED}, the value placed in the 0th element
     * in the returnContainer should be used.
     *
     * @param category
     * @param returnContainer
     * @return the extension result status type
     */
    public ExtensionResultStatusType getCategoryId(Long category, Long[] returnContainer);

    /**
     * In certain scenarios, the requested product id might not be the one that should be used in Solr.
     * If this method returns {@link ExtensionResultStatusType#HANDLED}, the value placed in the 0th element
     * in the returnContainer should be used.
     * 
     * @param product
     * @param returnContainer
     * @return the extension result status type
     */
    public ExtensionResultStatusType getProductId(Product product, Long[] returnContainer);
    
    /**
     * In certain scenarios, the requested sku id might not be the one that should be used in Solr.
     * If this method returns {@link ExtensionResultStatusType#HANDLED}, the value placed in the 0th element
     * in the returnContainer should be used.
     * 
     * @param sku
     * @param returnContainer
     * @return
     */
    public ExtensionResultStatusType getSkuId(Sku sku, Long[] returnContainer);
    
    public ExtensionResultStatusType modifyBuiltDocuments(Collection<SolrInputDocument> documents, List<Product> products, List<Field> fields, List<Locale> locales);

}
