
package com.wakacommerce.core.pricing.service;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.TaxException;

/**
 *
 * @ hui
 */
public interface TaxService {

    public Order calculateTaxForOrder(Order order) throws TaxException;

    public Order commitTaxForOrder(Order order) throws TaxException;

    public void cancelTax(Order order) throws TaxException;

}
