
package com.wakacommerce.common.i18n.service;

import java.util.List;

import com.wakacommerce.common.i18n.domain.ISOCountry;

/**
 *
 * @ hui
 */
public interface ISOService {

    public List<ISOCountry> findISOCountries();

    public ISOCountry findISOCountryByAlpha2Code(String alpha2);

    public ISOCountry save(ISOCountry isoCountry);

}
