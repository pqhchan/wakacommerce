
package com.wakacommerce.core.order.dao;

import java.util.List;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupFee;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.type.FulfillmentGroupStatusType;

public interface FulfillmentGroupDao {

    public FulfillmentGroup readFulfillmentGroupById(Long fulfillmentGroupId);

    public FulfillmentGroup save(FulfillmentGroup fulfillmentGroup);

    public FulfillmentGroup readDefaultFulfillmentGroupForOrder(Order order);

    public void delete(FulfillmentGroup fulfillmentGroup);

    public FulfillmentGroup createDefault();

    public FulfillmentGroup create();

    public FulfillmentGroupFee createFulfillmentGroupFee();

    public List<FulfillmentGroup> readUnfulfilledFulfillmentGroups(int start, int maxResults);

    public List<FulfillmentGroup> readPartiallyFulfilledFulfillmentGroups(int start, int maxResults);

    public List<FulfillmentGroup> readUnprocessedFulfillmentGroups(int start, int maxResults);

    public List<FulfillmentGroup> readFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults, boolean ascending);

    public List<FulfillmentGroup> readFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults);

    public Integer readNextFulfillmentGroupSequnceForOrder(Order order);
}
