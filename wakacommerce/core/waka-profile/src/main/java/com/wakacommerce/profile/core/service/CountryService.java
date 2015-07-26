
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.Country;

public interface CountryService {

    public List<Country> findCountries();

    public Country findCountryByAbbreviation(String abbreviation);
    
    public Country save(Country country);
    
}
