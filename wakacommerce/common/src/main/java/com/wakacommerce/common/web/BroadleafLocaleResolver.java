package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.locale.domain.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public interface BroadleafLocaleResolver  {

    @Deprecated
    public Locale resolveLocale(HttpServletRequest request);

    public Locale resolveLocale(WebRequest request);

}
