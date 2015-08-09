package com.wakacommerce.common.web;

import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.common.locale.service.LocaleService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
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
