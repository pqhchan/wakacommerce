
package com.wakacommerce.core.order.service;

import java.util.HashMap;

import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.GiftWrapOrderItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.PersonalMessage;
import com.wakacommerce.core.order.service.call.BundleOrderItemRequest;
import com.wakacommerce.core.order.service.call.DiscreteOrderItemRequest;
import com.wakacommerce.core.order.service.call.GiftWrapOrderItemRequest;
import com.wakacommerce.core.order.service.call.OrderItemRequest;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.core.order.service.call.ProductBundleOrderItemRequest;

public interface OrderItemService {
    
    public OrderItem readOrderItemById(Long orderItemId);

    public OrderItem saveOrderItem(OrderItem orderItem);
    
    public void delete(OrderItem item);
    
    public PersonalMessage createPersonalMessage();

    public DiscreteOrderItem createDiscreteOrderItem(DiscreteOrderItemRequest itemRequest);
    
    public DiscreteOrderItem createDynamicPriceDiscreteOrderItem(final DiscreteOrderItemRequest itemRequest, @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations);

    public GiftWrapOrderItem createGiftWrapOrderItem(GiftWrapOrderItemRequest itemRequest);

    public BundleOrderItem createBundleOrderItem(BundleOrderItemRequest itemRequest);

    public BundleOrderItem createBundleOrderItem(ProductBundleOrderItemRequest itemRequest);

    public BundleOrderItem createBundleOrderItem(ProductBundleOrderItemRequest itemRequest, boolean saveItem);

    public OrderItemRequestDTO buildOrderItemRequestDTOFromOrderItem(OrderItem item);

    public OrderItem updateDiscreteOrderItem(OrderItem orderItem, DiscreteOrderItemRequest itemRequest);

    public OrderItem createOrderItem(OrderItemRequest itemRequest);


}
