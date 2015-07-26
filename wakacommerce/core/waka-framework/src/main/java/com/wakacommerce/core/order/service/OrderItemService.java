
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

    /**
     * Used to create "manual" product bundles.   Manual product bundles are primarily designed
     * for grouping items in the cart display.    Typically ProductBundle will be used to
     * achieve non programmer related bundles.
     *
     *
     * @param itemRequest
     * @return
     */
    public BundleOrderItem createBundleOrderItem(BundleOrderItemRequest itemRequest);

    public BundleOrderItem createBundleOrderItem(ProductBundleOrderItemRequest itemRequest);

    public BundleOrderItem createBundleOrderItem(ProductBundleOrderItemRequest itemRequest, boolean saveItem);

    /**
     * Creates an OrderItemRequestDTO object that most closely resembles the given OrderItem.
     * That is, it will copy the SKU and quantity and attempt to copy the product and category
     * if they exist.
     * 
     * @param item the item to copy
     * @return the OrderItemRequestDTO that mirrors the item
     */
    public OrderItemRequestDTO buildOrderItemRequestDTOFromOrderItem(OrderItem item);

    public OrderItem updateDiscreteOrderItem(OrderItem orderItem, DiscreteOrderItemRequest itemRequest);

    public OrderItem createOrderItem(OrderItemRequest itemRequest);


}
