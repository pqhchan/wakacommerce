
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface Country extends Serializable {

    public String getAbbreviation();

    public void setAbbreviation(String abbreviation);

    public String getName();

    public void setName(String name);

}
