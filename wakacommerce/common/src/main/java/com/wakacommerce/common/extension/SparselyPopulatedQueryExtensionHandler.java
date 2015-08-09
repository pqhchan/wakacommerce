
package com.wakacommerce.common.extension;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @ hui
 */
public interface SparselyPopulatedQueryExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType refineRetrieve(Class<?> type, ResultType resultType, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Predicate> restrictions);

    ExtensionResultStatusType setup(Class<?> type, ResultType resultType);

    ExtensionResultStatusType breakdown(Class<?> type, ResultType resultType);

    ExtensionResultStatusType refineOrder(Class<?> type, ResultType resultType, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Order> sorts);

    ExtensionResultStatusType refineResults(Class<?> type, ResultType resultType, List queryResults, ExtensionResultHolder<List> response);

    ExtensionResultStatusType getResultType(Object testObject, ExtensionResultHolder<ResultType> response);

    ExtensionResultStatusType getCacheKey(Object testObject, String qualifier, ResultType resultType, ExtensionResultHolder<String> response);

    ExtensionResultStatusType getCacheKey(String qualifier, ResultType resultType, ExtensionResultHolder<String> response);

    ExtensionResultStatusType buildStatus(Class<?> type, List queryResults, ExtensionResultHolder<List<StandardCacheItem>> response);

    ExtensionResultStatusType isValidState(ExtensionResultHolder<Boolean> response);

}
