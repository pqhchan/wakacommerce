package com.wakacommerce.common.locale.service;

import java.util.List;

import com.wakacommerce.common.locale.domain.Locale;

public interface LocaleService {

    public Locale findLocaleByCode(String localeCode);

    public Locale findDefaultLocale();

    public List<Locale> findAllLocales();

    public Locale save(Locale locale);
    
}
