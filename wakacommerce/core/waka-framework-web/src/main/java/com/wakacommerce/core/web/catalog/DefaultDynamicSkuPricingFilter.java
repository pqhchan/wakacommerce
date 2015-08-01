
package com.wakacommerce.core.web.catalog;

import com.wakacommerce.core.catalog.service.dynamic.DynamicSkuPricingService;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Register this filter via Spring DelegatingFilterProxy, or register your own implementation
 * that provides additional, desirable members to the pricingConsiderations Map
 * that is generated from the getPricingConsiderations method.
 * 
 *  
 *
 */
public class DefaultDynamicSkuPricingFilter extends AbstractDynamicSkuPricingFilter {
    
    @Resource(name="blDynamicSkuPricingService")
    protected DynamicSkuPricingService skuPricingService;
    
    @Resource(name="blCustomerState")
    protected CustomerState customerState;

    public DynamicSkuPricingService getDynamicSkuPricingService(ServletRequest request) {
        return skuPricingService;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HashMap getPricingConsiderations(ServletRequest request) {
        HashMap pricingConsiderations = new HashMap();
        Customer customer = customerState.getCustomer((HttpServletRequest)  request);
        pricingConsiderations.put("customer", customer);
        
        return pricingConsiderations;
    }

}
