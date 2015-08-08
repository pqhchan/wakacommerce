  
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.CountrySubdivision;

public interface CountrySubdivisionDao {

    public List<CountrySubdivision> findSubdivisions();

    public List<CountrySubdivision> findSubdivisions(String countryAbbreviation);

    public List<CountrySubdivision> findSubdivisionsByCountryAndCategory(String countryAbbreviation, String category);

    public CountrySubdivision findSubdivisionByAbbreviation(String abbreviation);

    public CountrySubdivision create();

    public CountrySubdivision save(CountrySubdivision subdivision);

}
