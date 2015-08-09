
package com.wakacommerce.openadmin.dto;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;

/**
 *
 * @ hui
 */
public class CriteriaTransferObject {

    private Integer firstResult;
    private Integer maxResults;
    
    private Map<String, FilterAndSortCriteria> criteriaMap = new HashMap<String, FilterAndSortCriteria>();

    private List<FilterMapping> additionalFilterMappings = new ArrayList<FilterMapping>();
    private List<FilterMapping> nonCountAdditionalFilterMappings = new ArrayList<FilterMapping>();

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public void add(FilterAndSortCriteria criteria) {
        criteriaMap.put(criteria.getPropertyId(), criteria);
    }

    public void addAll(Collection<FilterAndSortCriteria> criterias) {
        for (FilterAndSortCriteria fasc : criterias) {
            add(fasc);
        }
    }

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

    public List<FilterMapping> getAdditionalFilterMappings() {
        return additionalFilterMappings;
    }
    
    public void setAdditionalFilterMappings(List<FilterMapping> additionalFilterMappings) {
        this.additionalFilterMappings = additionalFilterMappings;
    }

    public List<FilterMapping> getNonCountAdditionalFilterMappings() {
        return nonCountAdditionalFilterMappings;
    }

    public void setNonCountAdditionalFilterMappings(List<FilterMapping> nonCountAdditionalFilterMappings) {
        this.nonCountAdditionalFilterMappings = nonCountAdditionalFilterMappings;
    }
}
