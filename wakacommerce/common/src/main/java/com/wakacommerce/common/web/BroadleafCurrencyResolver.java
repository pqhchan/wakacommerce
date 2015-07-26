
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.currency.domain.BroadleafRequestedCurrencyDto;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: jerryocanas
 * Date: 9/6/12
 */

/**
 * Responsible for returning the currency to use for the current request.
 */
public interface BroadleafCurrencyResolver {

    /**
     * 
     * @deprecated use {@link #resolveCurrency(WebRequest)} instead
     */
    @Deprecated
    public BroadleafRequestedCurrencyDto resolveCurrency(HttpServletRequest request);
    
    public BroadleafRequestedCurrencyDto resolveCurrency(WebRequest request);

}
