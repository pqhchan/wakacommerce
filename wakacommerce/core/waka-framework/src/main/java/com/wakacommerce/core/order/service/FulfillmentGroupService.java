
package com.wakacommerce.core.order.service;

import java.util.List;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupFee;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderMultishipOption;
import com.wakacommerce.core.order.service.call.FulfillmentGroupItemRequest;
import com.wakacommerce.core.order.service.call.FulfillmentGroupRequest;
import com.wakacommerce.core.order.service.type.FulfillmentGroupStatusType;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.core.pricing.service.exception.PricingException;

public interface FulfillmentGroupService {

    public FulfillmentGroup save(FulfillmentGroup fulfillmentGroup);

    public FulfillmentGroup createEmptyFulfillmentGroup();

    public FulfillmentGroup findFulfillmentGroupById(Long fulfillmentGroupId);

    public void delete(FulfillmentGroup fulfillmentGroup);
    
    public FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest, boolean priceOrder) throws PricingException;
    
    public FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest, boolean priceOrder) throws PricingException;

    public FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest, boolean priceOrder, boolean save) throws PricingException;
    
    public Order removeAllFulfillmentGroupsFromOrder(Order order, boolean priceOrder) throws PricingException;

    public void removeOrderItemFromFullfillmentGroups(Order order, OrderItem orderItem);
    
    public FulfillmentGroupFee createFulfillmentGroupFee();

    public Order matchFulfillmentGroupsToMultishipOptions(Order order, boolean priceOrder) throws PricingException;

    public Order collapseToOneShippableFulfillmentGroup(Order order, boolean priceOrder) throws PricingException;

    public List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults);

    public List<FulfillmentGroup> findPartiallyFulfilledFulfillmentGroups(int start, int maxResults);

    public List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults);

    public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults, boolean ascending);

    public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults);

    public boolean isShippable(FulfillmentType fulfillmentType);

    public FulfillmentGroup getFirstShippableFulfillmentGroup(Order order);

    public List<FulfillmentGroupItem> getFulfillmentGroupItemsForOrderItem(Order order, OrderItem orderItem);


}
