
package com.wakacommerce.openadmin.server.service.persistence.module.criteria.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;

import java.util.List;

/**
 * 
 */
public interface PredicateProvider<T, Y> {

    Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder, From root, String ceilingEntity,
                             String fullPropertyName, Path<T> explicitPath, List<Y> directValues);

}
