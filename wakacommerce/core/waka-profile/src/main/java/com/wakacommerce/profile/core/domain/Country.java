
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 * This entity should be used only for lookup and filtering purposes only.
 * For example, to help populate a drop-down to those Countries you only wish to ship to.
 *
 * {@link com.wakacommerce.profile.core.domain.Address} no longer references this and Address
 * implementations should be updated to use {@link com.wakacommerce.common.i18n.domain.ISOCountry} instead.
 * This is to accommodate International Billing/Shipping Addresses which may not necessarily be restricted
 * to countries represented by this entity.
 *
 * {@link http://www.iso.org/iso/country_codes.htm}
 * {@link http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2}
 *
 *  
 */
public interface Country extends Serializable {

    /**
     * The primary key - Ideally, implementations should use the ISO 3166-1 alpha-2 code of the country.
     * e.g. "US" or "GB"
     * {@link http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2}
     */
    public String getAbbreviation();

    /**
     * sets the abbreviation for this Country
     * @param abbreviation - e.g. "US" or "GB"
     */
    public void setAbbreviation(String abbreviation);

    /**
     * The name for the Country
     * e.g. "United States", "United Kingdom"
     * @return - the name of the Country
     */
    public String getName();

    /**
     * sets the name of the Country
     * @param name - e.g. "United States", "United Kingdom"
     */
    public void setName(String name);

}
