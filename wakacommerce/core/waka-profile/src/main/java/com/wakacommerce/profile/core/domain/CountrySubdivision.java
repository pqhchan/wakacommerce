package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 * {@link http://www.iso.org/iso/country_codes.htm}
 * {@link http://en.wikipedia.org/wiki/ISO_3166-2}
 */
public interface CountrySubdivision extends Serializable {

    /**
     * The primary key - Ideally, implementations should use the ISO 3166-2 code of the country subdivision.
     * e.g. "US-TX" or "GB-WSM"
     * {@link http://en.wikipedia.org/wiki/ISO_3166-2}
     */
    public String getAbbreviation();

    /**
     * Sets the primary key abbreviation
     * @param abbreviation - e.g. "US-TX" or "GB-WSM"
     */
    public void setAbbreviation(String abbreviation);

    /**
     * More friendly abbreviation that can be used for display purposes
     * e.g. "TX", "CA", "NY"
     * @return - the alternate abbreviation
     */
    public String getAlternateAbbreviation();

    /**
     * sets the alternate abbreviation
     * @param alternateAbbreviation - e.g. "TX", "CA", "NY"
     */
    public void setAlternateAbbreviation(String alternateAbbreviation);

    /**
     * Full name for display purposes
     * e.g. "Texas", "California", "New York"
     * @return - the alternate abbreviation
     */
    public String getName();

    /**
     * sets the name
     * @param name - e.g. "Texas", "California", "New York"
     */
    public void setName(String name);

    /**
     * A category that represents the subdivision of this Country.
     * e.g. "State", "Province", "District"
     * @return - the country subdivision category
     */
    public CountrySubdivisionCategory getCategory();

    /**
     * sets the country subdivision category
     * @param category - e.g. "State", "Province", "District"
     */
    public void setCategory(CountrySubdivisionCategory category);

    /**
     * The country where this subdivision resides
     * e.g. "US", "GB"
     * @return - the country where this subdivision resides
     */
    public Country getCountry();

    /**
     * sets the country in which this subdivision resides
     * @param country - e.g. "US", "GB"
     */
    public void setCountry(Country country);


}
