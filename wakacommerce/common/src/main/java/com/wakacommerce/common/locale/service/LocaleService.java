package com.wakacommerce.common.locale.service;

import java.util.List;

import com.wakacommerce.common.locale.domain.Locale;

public interface LocaleService {

    /**
     * @return the locale for the passed in code
     */
    public Locale findLocaleByCode(String localeCode);

    /**
     * @return the default locale
     */
    public Locale findDefaultLocale();

    /**
     * @return a list of all known locales
     */
    public List<Locale> findAllLocales();
    
    /**
     * Persists the given locale
     * 
     * @param locale
     * @return the persisted locale
     */
    public Locale save(Locale locale);
    
}
