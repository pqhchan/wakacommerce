
package com.wakacommerce.core.pricing.service;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.PricingException;

public interface PricingService {

    public Order executePricing(Order order) throws PricingException;

}
