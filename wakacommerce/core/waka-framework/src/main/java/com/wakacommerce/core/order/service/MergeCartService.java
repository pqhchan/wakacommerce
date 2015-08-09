
package com.wakacommerce.core.order.service;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.call.MergeCartResponse;
import com.wakacommerce.core.order.service.call.ReconstructCartResponse;
import com.wakacommerce.core.order.service.exception.RemoveFromCartException;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.profile.core.domain.Customer;

/**
 *
 * @ hui
 */
public interface MergeCartService {

    public MergeCartResponse mergeCart(Customer customer, Order anonymousCart, boolean priceOrder) throws PricingException, RemoveFromCartException;

    public MergeCartResponse mergeCart(Customer customer, Order anonymousCart) throws PricingException, RemoveFromCartException;

    public ReconstructCartResponse reconstructCart(Customer customer, boolean priceOrder) throws PricingException, RemoveFromCartException;

    public ReconstructCartResponse reconstructCart(Customer customer) throws PricingException, RemoveFromCartException;

}
