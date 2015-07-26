
package com.wakacommerce.openadmin.dto;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;

/**
 * Generic persistent entity criteria transfer object.
 * Provides a storage mechanism for query related information regarding an entity.
 * 
 *Jeff Fischer
 */
public class CriteriaTransferObject {

    private Integer firstResult;
    private Integer maxResults;
    
    private Map<String, FilterAndSortCriteria> criteriaMap = new HashMap<String, FilterAndSortCriteria>();

    private List<FilterMapping> additionalFilterMappings = new ArrayList<FilterMapping>();
    private List<FilterMapping> nonCountAdditionalFilterMappings = new ArrayList<FilterMapping>();
    
    /**
     * The index of records in the database for which a fetch will start.
     *
     * @return the index to start, or null
     */
    public Integer getFirstResult() {
        return firstResult;
    }
    
    /**
     * The index of records in the datastore for which a fetch will start.
     *
     * @param firstResult the index to start, or null
     */
    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }
    
    /**
     * The max number of records from the datastore to return.
     *
     * @return the max records, or null
     */
    public Integer getMaxResults() {
        return maxResults;
    }
    
    /**
     * The max number of records from the datastore to return.
     *
     * @param maxResults the max records, or null
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
    
    /**
     * Add a {@link FilterAndSortCriteria} instance. Contains information about which records are retrieved
     * and in what direction they're sorted.
     * 
     * @param criteria {@link FilterAndSortCriteria}
     */
    public void add(FilterAndSortCriteria criteria) {
        criteriaMap.put(criteria.getPropertyId(), criteria);
    }
    
    /**
     * Add all {@link FilterAndSortCriteria} instances. Contains information about which records are retrieved
     * and in what direction they're sorted.
     * 
     * @param criterias the list of {@link FilterAndSortCriteria} instances to add
     */
    public void addAll(Collection<FilterAndSortCriteria> criterias) {
        for (FilterAndSortCriteria fasc : criterias) {
            add(fasc);
        }
    }

    /**
     * Retrieve the added {@link FilterAndSortCriteria} instances organized into a map
     *
     * @return the {@link FilterAndSortCriteria} instances as a map
     */
    public Map<String, FilterAndSortCriteria> getCriteriaMap() {
        return criteriaMap;
    }
    
    public void setCriteriaMap(Map<String, FilterAndSortCriteria> criteriaMap) {
        this.criteriaMap = criteriaMap;
    }

    public FilterAndSortCriteria get(String name) {
        if (criteriaMap.containsKey(name)) {
            return criteriaMap.get(name);
        }
        FilterAndSortCriteria criteria = new FilterAndSortCriteria(name);
        criteriaMap.put(name, criteria);
        return criteriaMap.get(name);
    }

    public void defaultSortDirectionForFieldIfUnset(String name, SortDirection defaultDirection) {
        FilterAndSortCriteria fsc = get(name);
        if (fsc.getSortDirection() == null) {
            fsc.setSortDirection(defaultDirection);
        }
    }

    /**
     * This list holds additional filter mappings that might have been constructed in a custom persistence
     * handler. This is only used when very custom filtering needs to occur.
     */
    public List<FilterMapping> getAdditionalFilterMappings() {
        return additionalFilterMappings;
    }
    
    public void setAdditionalFilterMappings(List<FilterMapping> additionalFilterMappings) {
        this.additionalFilterMappings = additionalFilterMappings;
    }

    /**
     * This list holds additional filter mappings that might have been constructed in a custom persistence
     * handler. This is only used when very custom filtering needs to occur.
     *
     * These filter mappings will NOT be applied to the query that gathers the total number of results.
     * This especially applies to queries that include join fetches where the total number of results
     * should not include the join fetched items. An example of this is defaultSku and defaultCategory
     * being join fetched when querying for a set of products. In this case, these filter mappings are
     * applied to also return the defaultSku and defaultCategory of each product, but the total number
     * of results should only include the number of products.
     */
    public List<FilterMapping> getNonCountAdditionalFilterMappings() {
        return nonCountAdditionalFilterMappings;
    }

    public void setNonCountAdditionalFilterMappings(List<FilterMapping> nonCountAdditionalFilterMappings) {
        this.nonCountAdditionalFilterMappings = nonCountAdditionalFilterMappings;
    }
}
