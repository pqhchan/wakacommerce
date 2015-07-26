
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 * @deprecated - use {@link com.wakacommerce.profile.core.domain.CountrySubdivision} instead.
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
