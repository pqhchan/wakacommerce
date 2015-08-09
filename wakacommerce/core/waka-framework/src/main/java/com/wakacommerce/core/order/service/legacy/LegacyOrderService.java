
package com.wakacommerce.core.order.service.legacy;

import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.call.BundleOrderItemRequest;
import com.wakacommerce.core.order.service.call.DiscreteOrderItemRequest;
import com.wakacommerce.core.order.service.call.FulfillmentGroupRequest;
import com.wakacommerce.core.order.service.call.GiftWrapOrderItemRequest;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.core.order.service.exception.ItemNotFoundException;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
@Deprecated
public interface LegacyOrderService extends OrderService {

    public FulfillmentGroup findDefaultFulfillmentGroupForOrder(Order order);

    public OrderItem addGiftWrapItemToOrder(Order order, GiftWrapOrderItemRequest itemRequest) throws PricingException;

    public OrderItem addBundleItemToOrder(Order order, BundleOrderItemRequest itemRequest) throws PricingException;

    public OrderItem addBundleItemToOrder(Order order, BundleOrderItemRequest itemRequest, boolean priceOrder) throws PricingException;

    public OrderPayment addPaymentToOrder(Order order, OrderPayment payment);

    public FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest) throws PricingException;
    
    public FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest, boolean priceOrder) throws PricingException;

    public FulfillmentGroup addFulfillmentGroupToOrder(Order order, FulfillmentGroup fulfillmentGroup) throws PricingException;
    
    public FulfillmentGroup addFulfillmentGroupToOrder(Order order, FulfillmentGroup fulfillmentGroup, boolean priceOrder) throws PricingException;

    public FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup, int quantity) throws PricingException;
    
    public FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup, int quantity, boolean priceOrder) throws PricingException;

    public FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup) throws PricingException;
    
    public FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup, boolean priceOrder) throws PricingException;

    public FulfillmentGroup addItemToFulfillmentGroup(Order order, OrderItem item, FulfillmentGroup fulfillmentGroup, int quantity, boolean priceOrder) throws PricingException;

    public void updateItemQuantity(Order order, OrderItem item) throws ItemNotFoundException, PricingException;

    public void updateItemQuantity(Order order, OrderItem item, boolean priceOrder) throws ItemNotFoundException, PricingException;

    public void updateItemQuantity(Order order, OrderItemRequestDTO orderItemRequestDTO) throws ItemNotFoundException, PricingException;

    public void removeFulfillmentGroupFromOrder(Order order, FulfillmentGroup fulfillmentGroup) throws PricingException;
    
    public void removeFulfillmentGroupFromOrder(Order order, FulfillmentGroup fulfillmentGroup, boolean priceOrder) throws PricingException;

    public Order removeItemFromOrder(Order order, OrderItem item) throws PricingException;
    
    public Order removeItemFromOrder(Order order, OrderItem item, boolean priceOrder) throws PricingException;

    public void removeNamedOrderForCustomer(String name, Customer customer);

    public void removeAllFulfillmentGroupsFromOrder(Order order) throws PricingException;

    public void removeAllFulfillmentGroupsFromOrder(Order order, boolean priceOrder) throws PricingException;

    public List<OrderPayment> readPaymentInfosForOrder(Order order);

    public Order removeItemFromOrder(Long orderId, Long itemId) throws PricingException;
    
    public Order removeItemFromOrder(Long orderId, Long itemId, boolean priceOrder) throws PricingException;

    public FulfillmentGroup createDefaultFulfillmentGroup(Order order, Address address);

    public Order addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws PricingException;

    public DiscreteOrderItemRequest createDiscreteOrderItemRequest(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity);

    public Order addOrUpdateOrderItemAttributes(Order order, OrderItem item, Map<String,String> attributeValues, boolean priceOrder) throws ItemNotFoundException, PricingException;

    public Order removeOrderItemAttribute(Order order, OrderItem item, String attributeName, boolean priceOrder) throws ItemNotFoundException, PricingException;

    @Deprecated
    public OrderItem addDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest) throws PricingException;

    @Deprecated
    public OrderItem addDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest, boolean priceOrder) throws PricingException;

    @Deprecated
    public OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity) throws PricingException;

    @Deprecated
    public OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity, Map<String,String> orderItemAttributes) throws PricingException;

    @Deprecated
    public OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity, boolean priceOrder) throws PricingException;

    @Deprecated
    public OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity, boolean priceOrder, Map<String,String> orderItemAttributes) throws PricingException;

    @Deprecated
    public OrderItem addOrderItemToOrder(Order order, OrderItem newOrderItem) throws PricingException;

    @Deprecated
    public OrderItem addOrderItemToOrder(Order order, OrderItem newOrderItem, boolean priceOrder) throws PricingException;

    @Deprecated
    public OrderItem addDynamicPriceDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest, @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations) throws PricingException;

    @Deprecated
    public OrderItem addDynamicPriceDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest, @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations, boolean priceOrder) throws PricingException;

    OrderItem addOrderItemToBundle(Order order, BundleOrderItem bundle, DiscreteOrderItem newOrderItem, boolean priceOrder) throws PricingException;

    Order removeItemFromBundle(Order order, BundleOrderItem bundle, OrderItem item, boolean priceOrder) throws PricingException;

}
