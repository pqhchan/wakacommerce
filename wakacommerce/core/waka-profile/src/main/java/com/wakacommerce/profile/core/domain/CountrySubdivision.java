
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface CountrySubdivision extends Serializable {

    public String getAbbreviation();

    public void setAbbreviation(String abbreviation);

    public String getAlternateAbbreviation();

    public void setAlternateAbbreviation(String alternateAbbreviation);

    public String getName();

    public void setName(String name);

    public CountrySubdivisionCategory getCategory();

    public void setCategory(CountrySubdivisionCategory category);

    public Country getCountry();

    public void setCountry(Country country);


}
