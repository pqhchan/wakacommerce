
package com.wakacommerce.core.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wakacommerce.common.config.domain.ModuleConfiguration;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;

/**
 *
 * @ hui
 */
public interface TaxDetail extends Serializable {

    public Long getId();

    public void setId(Long id);

    public TaxType getType();

    public void setType(TaxType type);

    public Money getAmount();

    public void setAmount(Money amount);

    public BigDecimal getRate();

    public void setRate(BigDecimal rate);
    
    public BroadleafCurrency getCurrency();

    public void setCurrency(BroadleafCurrency currency);

    public ModuleConfiguration getModuleConfiguration();

    public void setModuleConfiguration(ModuleConfiguration config);

    public void setJurisdictionName(String jurisdiction);

    public String getJurisdictionName();

    public void setTaxName(String taxName);

    public String getTaxName();

    public void setRegion(String region);

    public String getRegion();

    public void setCountry(String country);

    public String getCountry();

}
