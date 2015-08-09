package com.wakacommerce.common.locale.domain;

import java.io.Serializable;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;

  
public interface Locale extends Serializable {

    String getLocaleCode();

    void setLocaleCode(String localeCode);

    public String getFriendlyName();

    public void setFriendlyName(String friendlyName);

    public void setDefaultFlag(Boolean defaultFlag);

    public Boolean getDefaultFlag();

    public BroadleafCurrency getDefaultCurrency();

    public void setDefaultCurrency(BroadleafCurrency currency);

    public Boolean getUseCountryInSearchIndex();

    public void setUseCountryInSearchIndex(Boolean useInSearchIndex);

}
