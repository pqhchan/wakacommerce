
package com.wakacommerce.core.order.service.legacy;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.call.MergeCartResponse;
import com.wakacommerce.core.order.service.call.ReconstructCartResponse;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.profile.core.domain.Customer;

/**
 *
 * @ hui
 */
@Deprecated
public interface LegacyCartService extends LegacyOrderService {

    Order addAllItemsToCartFromNamedOrder(Order namedOrder) throws PricingException;
    
    Order addAllItemsToCartFromNamedOrder(Order namedOrder, boolean priceOrder) throws PricingException;

    OrderItem moveItemToCartFromNamedOrder(Order order, OrderItem orderItem) throws PricingException;
    
    OrderItem moveItemToCartFromNamedOrder(Order order, OrderItem orderItem, boolean priceOrder) throws PricingException;

    OrderItem moveItemToCartFromNamedOrder(Long customerId, String orderName, Long orderItemId, Integer quantity) throws PricingException;
    
    OrderItem moveItemToCartFromNamedOrder(Long customerId, String orderName, Long orderItemId, Integer quantity, boolean priceOrder) throws PricingException;

    Order moveAllItemsToCartFromNamedOrder(Order namedOrder) throws PricingException;
    
    Order moveAllItemsToCartFromNamedOrder(Order namedOrder, boolean priceOrder) throws PricingException;

    public MergeCartResponse mergeCart(Customer customer, Order anonymousCart, boolean priceOrder) throws PricingException;
    public MergeCartResponse mergeCart(Customer customer, Order anonymousCart) throws PricingException;

    public ReconstructCartResponse reconstructCart(Customer customer, boolean priceOrder) throws PricingException;
    public ReconstructCartResponse reconstructCart(Customer customer) throws PricingException;

}
