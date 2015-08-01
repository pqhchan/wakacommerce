package com.wakacommerce.common.web;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.locale.service.LocaleService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Specific Spring component to override the default behavior of {@link CookieLocaleResolver} so that the default Broadleaf
 * Locale looked up in the database is used. This should be hooked up in applicationContext-servlet.xml in place of Spring's
 * {@link CookieResolver}.
 * 
 * @see {@link WakaLocaleResolverImpl}
 */
public class WakaCookieLocaleResolver extends CookieLocaleResolver {

    @Resource(name = "blLocaleService")
    private LocaleService localeService;
    
    @Override
    protected java.util.Locale determineDefaultLocale(HttpServletRequest request) {
        java.util.Locale defaultLocale = getDefaultLocale();
        if (defaultLocale == null) {
            Locale defaultBroadleafLocale = localeService.findDefaultLocale();
            if (defaultBroadleafLocale == null) {
                return super.determineDefaultLocale(request);
            } else {
                return WakaRequestContext.convertLocaleToJavaLocale(defaultBroadleafLocale);
            }
        }
        return defaultLocale;
    }
    
}
