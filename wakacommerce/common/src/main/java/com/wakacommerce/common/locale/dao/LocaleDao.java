
package com.wakacommerce.common.locale.dao;

import java.util.List;

import com.wakacommerce.common.locale.domain.Locale;

/**
 *   
 */
public interface LocaleDao {

    /**
     * @return The locale for the passed in code
     */
    public Locale findLocaleByCode(String localeCode);

    /**
     * Returns the page template with the passed in id.
     *
     * @return The default locale
     */
    public Locale findDefaultLocale();

    /**
     * Returns all supported BLC locales.
     * @return
     */
    public List<Locale> findAllLocales();
    
    public Locale save(Locale locale);

}
