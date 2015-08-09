package com.wakacommerce.common.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.locale.service.LocaleService;
import com.wakacommerce.common.util.BLCRequestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@Component("blLocaleResolver")
public class WakaLocaleResolverImpl implements BroadleafLocaleResolver {
    private final Log LOG = LogFactory.getLog(WakaLocaleResolverImpl.class);

    public static String LOCALE_VAR = "blLocale";

    public static String LOCALE_CODE_PARAM = "blLocaleCode";

    public static String LOCALE_PULLED_FROM_SESSION = "blLocalePulledFromSession";

    @Resource(name = "blLocaleService")
    private LocaleService localeService;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return resolveLocale(new ServletWebRequest(request));
    }

    @Override
    public Locale resolveLocale(WebRequest request) {
        Locale locale = null;

        // First check for request attribute
        locale = (Locale) request.getAttribute(LOCALE_VAR, WebRequest.SCOPE_REQUEST);

        // Second, check for a request parameter
        if (locale == null && BLCRequestUtils.getURLorHeaderParameter(request, LOCALE_CODE_PARAM) != null) {
            String localeCode = BLCRequestUtils.getURLorHeaderParameter(request, LOCALE_CODE_PARAM);
            locale = localeService.findLocaleByCode(localeCode);
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                request.removeAttribute(BroadleafCurrencyResolverImpl.CURRENCY_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("Attempt to find locale by param " + localeCode + " resulted in " + locale);
            }
        }

        // Third, check the session
        if (locale == null && BLCRequestUtils.isOKtoUseSession(request)) {
            locale = (Locale) request.getAttribute(LOCALE_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Attempt to find locale from session resulted in " + locale);
            }

            if (locale != null) {
                request.setAttribute(LOCALE_PULLED_FROM_SESSION, Boolean.TRUE, WebRequest.SCOPE_REQUEST);
            }

        }

        // Finally, use the default
        if (locale == null) {
            locale = localeService.findDefaultLocale();
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                request.removeAttribute(BroadleafCurrencyResolverImpl.CURRENCY_VAR, WebRequest.SCOPE_GLOBAL_SESSION);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("Locale set to default locale " + locale);
            }
        }
        
        // Set the default locale to override Spring's cookie resolver
        request.setAttribute(LOCALE_VAR, locale, WebRequest.SCOPE_REQUEST);
        java.util.Locale javaLocale = WakaRequestContext.convertLocaleToJavaLocale(locale);
        request.setAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME, javaLocale, WebRequest.SCOPE_REQUEST);

        if (BLCRequestUtils.isOKtoUseSession(request)) {
            request.setAttribute(LOCALE_VAR, locale, WebRequest.SCOPE_GLOBAL_SESSION);
        }
        return locale;
    }
}
