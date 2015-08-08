  
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 * Represents a category that describes a Countries subdivision.
 * e.g. "State", "Province", "Region", "District", "Territory" etc...
 *
 * {@link http://www.iso.org/iso/country_codes.htm}
 * {@link http://en.wikipedia.org/wiki/ISO_3166-2}
 *
 *  
 */
public interface CountrySubdivisionCategory extends Serializable {

    public void setId(Long id);

    public Long getId();

    public String getName();

    public void setName(String name);

}
