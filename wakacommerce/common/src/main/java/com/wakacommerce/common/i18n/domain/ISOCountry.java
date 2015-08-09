
package com.wakacommerce.common.i18n.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.i18n.service.type.ISOCodeStatusType;

/**
 *
 * @ hui
 */
public interface ISOCountry extends Serializable {

    public String getAlpha2();

    public void setAlpha2(String alpha2);

    public String getName();

    public void setName(String name);

    public String getAlpha3();

    public void setAlpha3(String alpha3);

    public Integer getNumericCode();

    public void setNumericCode(Integer numericCode);

    public ISOCodeStatusType getStatus();

    public void setStatus(ISOCodeStatusType status);

}
