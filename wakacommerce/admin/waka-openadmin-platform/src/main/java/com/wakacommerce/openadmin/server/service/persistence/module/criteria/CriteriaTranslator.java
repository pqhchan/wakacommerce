
package com.wakacommerce.openadmin.server.service.persistence.module.criteria;

import javax.persistence.TypedQuery;

import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */
public interface CriteriaTranslator {

    TypedQuery<Serializable> translateQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity, List<FilterMapping> filterMappings, Integer firstResult, Integer maxResults);

    TypedQuery<Serializable> translateCountQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity, List<FilterMapping> filterMappings);

    TypedQuery<Serializable> translateMaxQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity, List<FilterMapping> filterMappings, String maxField);
}
