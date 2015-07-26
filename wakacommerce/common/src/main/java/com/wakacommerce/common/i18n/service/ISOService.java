
package com.wakacommerce.common.i18n.service;

import java.util.List;

import com.wakacommerce.common.i18n.domain.ISOCountry;

/**
 * Service that provide methods to look up the
 * standards published by the International Organization for Standardization (ISO)
 *
 * For example, ISO 3166-1 define codes for countries/dependent territories that are widely used
 * by many systems. You can use this service to find the defined countries based on the alpha-2 code for that country.
 *
 *Elbert Bautista (elbertbautista)
 */
public interface ISOService {

    public List<ISOCountry> findISOCountries();

    public ISOCountry findISOCountryByAlpha2Code(String alpha2);

    public ISOCountry save(ISOCountry isoCountry);

}
