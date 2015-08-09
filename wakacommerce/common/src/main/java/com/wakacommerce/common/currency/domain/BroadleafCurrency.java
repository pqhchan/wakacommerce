
package com.wakacommerce.common.currency.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface BroadleafCurrency extends Serializable {

    public String getCurrencyCode();

    public void setCurrencyCode(String code);

    public String getFriendlyName();

    public void setFriendlyName(String friendlyName);

    public boolean getDefaultFlag();

    public void setDefaultFlag(boolean defaultFlag);
}
