
package com.wakacommerce.core.offer.service;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.PricingException;

/**
 *
 * @ hui
 */
public interface ShippingOfferService {
    
    public void reviewOffers(Order order) throws PricingException;
    
}
