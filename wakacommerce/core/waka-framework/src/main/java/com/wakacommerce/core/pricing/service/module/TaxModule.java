
package com.wakacommerce.core.pricing.service.module;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.TaxService;
import com.wakacommerce.core.pricing.service.exception.TaxException;
import com.wakacommerce.core.pricing.service.tax.provider.TaxProvider;

/**
 * @deprectated use {@link TaxService} and {@link TaxProvider}s instead
 */
@Deprecated
public interface TaxModule {
    
    public String getName();
    
    public void setName(String name);
    
    public Order calculateTaxForOrder(Order order) throws TaxException;
    
}
