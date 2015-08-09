
package com.wakacommerce.openadmin.server.service.persistence.module.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.wakacommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider;

/**
 *
 * @ hui
 */
public interface CriteriaTranslatorEventHandler {

    void addRestrictions(String ceilingEntity, List<FilterMapping> filterMappings, CriteriaBuilder criteriaBuilder,
                         Root original, List<Predicate> restrictions, List<Order> sorts, CriteriaQuery criteria);

}
