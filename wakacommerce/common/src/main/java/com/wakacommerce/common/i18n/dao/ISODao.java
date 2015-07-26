
package com.wakacommerce.common.i18n.dao;

import java.util.List;

import com.wakacommerce.common.i18n.domain.ISOCountry;

/**
 *Elbert Bautista (elbertbautista)
 */
public interface ISODao {

    public List<ISOCountry> findISOCountries();

    public ISOCountry findISOCountryByAlpha2Code(String alpha2);

    public ISOCountry save(ISOCountry isoCountry);

}
