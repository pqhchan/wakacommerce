package com.wakacommerce.common.extension;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface QueryExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType setup(Class<?> type, String[] config);

    public ExtensionResultStatusType refineRetrieve(Class<?> type, String[] config, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Predicate> restrictions);

    public ExtensionResultStatusType refineOrder(Class<?> type, String[] config, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Order> sorts);

    public ExtensionResultStatusType refineResults(Class<?> type, String[] config, List queryResults, ExtensionResultHolder<List> response);

    public ExtensionResultStatusType breakdown(Class<?> type, String[] config);

}
