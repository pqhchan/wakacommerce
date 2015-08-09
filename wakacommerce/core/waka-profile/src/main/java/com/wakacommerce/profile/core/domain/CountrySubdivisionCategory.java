
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface CountrySubdivisionCategory extends Serializable {

    public void setId(Long id);

    public Long getId();

    public String getName();

    public void setName(String name);

}
