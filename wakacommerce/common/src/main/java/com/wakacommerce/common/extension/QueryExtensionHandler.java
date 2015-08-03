package com.wakacommerce.common.extension;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface QueryExtensionHandler extends ExtensionHandler {

    /**
     * Perform any setup operations. This is usually done before executing the query and can serve to prepare the WakaRequestContext (if applicable).
     *
     * @param type the class type for the query (can be null)
     * @param config pass information to the handler, perhaps to be used by the handler to determine suitability (can be null)
     * @return the status of the extension operation
     */
    public ExtensionResultStatusType setup(Class<?> type, String[] config);

    /**
     * Add additional restrictions to the fetch query
     *
     * @param type the class type for the query (can be null)
     * @param config pass information to the handler, perhaps to be used by the handler to determine suitability (can be null)
     * @param builder
     * @param criteria
     * @param root
     * @param restrictions any additional JPA criteria restrictions should be added here
     * @return the status of the extension operation
     */
    public ExtensionResultStatusType refineRetrieve(Class<?> type, String[] config, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Predicate> restrictions);

    /**
     * Add sorting to the fetch query
     *
     * @param type the class type for the query (can be null)
     * @param config pass information to the handler, perhaps to be used by the handler to determine suitability (can be null)
     * @param builder
     * @param criteria
     * @param root
     * @param sorts any additional JPA order expressions should be added here
     * @return the status of the extension operation
     */
    public ExtensionResultStatusType refineOrder(Class<?> type, String[] config, CriteriaBuilder builder, CriteriaQuery criteria, Root root, List<Order> sorts);

    /**
     * Filter the results from the database in Java
     *
     * @param type the class type for the query (can be null)
     * @param config pass information to the handler, perhaps to be used by the handler to determine suitability (can be null)
     * @param queryResults the results of the fetch query from the database
     * @param response the container for the filtered results
     * @return the status of the extension operation
     */
    public ExtensionResultStatusType refineResults(Class<?> type, String[] config, List queryResults, ExtensionResultHolder<List> response);

    /**
     * Perform any breakdown operations. This is usually done after executing the query and can serve to reset the WakaRequestContext (if applicable)
     *
     * @param type the class type for the query (can be null)
     * @param config pass information to the handler, perhaps to be used by the handler to determine suitability (can be null)
     * @return the status of the extension operation
     */
    public ExtensionResultStatusType breakdown(Class<?> type, String[] config);

}
