
package com.wakacommerce.core.order.service.legacy;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.call.MergeCartResponse;
import com.wakacommerce.core.order.service.call.ReconstructCartResponse;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.profile.core.domain.Customer;

/**
 * This legacy interface should no longer be used as of 2.0
 * 
 * The new interface and implementation are OrderService and OrderServiceImpl
 * 
 * @deprecated
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

    /**
     * Merge the anonymous cart with the customer's cart taking into
     * consideration sku activation
     * @param customer the customer whose cart is to be merged
     * @param anonymousCartId the anonymous cart id
     * @return the response containing the cart, any items added to the cart,
     *         and any items removed from the cart
     */
    public MergeCartResponse mergeCart(Customer customer, Order anonymousCart, boolean priceOrder) throws PricingException;
    public MergeCartResponse mergeCart(Customer customer, Order anonymousCart) throws PricingException;
    
    /**
     * Reconstruct the cart using previous stored state taking into
     * consideration sku activation
     * @param customer the customer whose cart is to be reconstructed
     * @return the response containing the cart and any items removed from the
     *         cart
     */
    public ReconstructCartResponse reconstructCart(Customer customer, boolean priceOrder) throws PricingException;
    public ReconstructCartResponse reconstructCart(Customer customer) throws PricingException;

}
