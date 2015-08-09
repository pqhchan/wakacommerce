
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.currency.domain.BroadleafRequestedCurrencyDto;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */

/**
 *
 * @ hui
 */
public interface BroadleafCurrencyResolver {

    @Deprecated
    public BroadleafRequestedCurrencyDto resolveCurrency(HttpServletRequest request);
    
    public BroadleafRequestedCurrencyDto resolveCurrency(WebRequest request);

}
