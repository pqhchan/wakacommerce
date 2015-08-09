
package com.wakacommerce.core.pricing.service.tax.provider;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.config.service.ModuleProvider;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.TaxException;

/**
 *
 * @ hui
 */
public interface TaxProvider extends ModuleProvider {

    public Order calculateTaxForOrder(Order order, ModuleConfiguration config) throws TaxException;

    public Order commitTaxForOrder(Order order, ModuleConfiguration config) throws TaxException;

    public void cancelTax(Order order, ModuleConfiguration config) throws TaxException;
}
