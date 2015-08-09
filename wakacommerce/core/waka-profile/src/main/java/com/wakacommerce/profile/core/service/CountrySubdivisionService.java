
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.CountrySubdivision;

/**
 *
 * @ hui
 */
public interface CountrySubdivisionService {

    public List<CountrySubdivision> findSubdivisions();

    public List<CountrySubdivision> findSubdivisions(String countryAbbreviation);

    public List<CountrySubdivision> findSubdivisionsByCountryAndCategory(String countryAbbreviation, String category);

    public CountrySubdivision findSubdivisionByAbbreviation(String abbreviation);

    public CountrySubdivision save(CountrySubdivision subdivision);

}
