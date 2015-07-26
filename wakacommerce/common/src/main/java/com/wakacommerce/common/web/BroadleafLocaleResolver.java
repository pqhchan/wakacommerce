
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.locale.domain.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Responsible for returning the Locale to use for the current request.
 *
 *bpolster
 */
public interface BroadleafLocaleResolver  {

    /**
     * @deprecated Use {@link #resolveLocale(WebRequest)} instead
     */
    @Deprecated
    public Locale resolveLocale(HttpServletRequest request);

    public Locale resolveLocale(WebRequest request);

}
