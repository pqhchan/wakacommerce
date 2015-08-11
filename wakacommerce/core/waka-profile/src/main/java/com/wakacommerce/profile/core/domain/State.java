package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
@Deprecated
public interface State extends Serializable {

    public String getAbbreviation();

    public void setAbbreviation(String abbreviation);

    public String getName();

    public void setName(String name);

    public Country getCountry();

    public void setCountry(Country country);
}
