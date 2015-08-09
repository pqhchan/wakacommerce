
package com.wakacommerce.common.locale.dao;

import java.util.List;

import com.wakacommerce.common.locale.domain.Locale;

/**
 *
 * @ hui
 */
public interface LocaleDao {

    public Locale findLocaleByCode(String localeCode);

    public Locale findDefaultLocale();

    public List<Locale> findAllLocales();
    
    public Locale save(Locale locale);

}
