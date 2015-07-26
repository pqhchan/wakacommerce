
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.Country;

public interface CountryDao {

    public List<Country> findCountries();

    public Country findCountryByAbbreviation(String abbreviation);
    
    public Country save(Country country);
    
}
