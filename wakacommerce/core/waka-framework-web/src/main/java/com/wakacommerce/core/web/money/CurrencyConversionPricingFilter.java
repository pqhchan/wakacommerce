
package com.wakacommerce.core.web.money;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;

import com.wakacommerce.common.money.CurrencyConversionService;

import java.util.HashMap;

public interface CurrencyConversionPricingFilter extends Filter {
    
    @SuppressWarnings("rawtypes")
    public HashMap getCurrencyConversionContext(ServletRequest request);
    
    public CurrencyConversionService getCurrencyConversionService(ServletRequest request);
    
}
