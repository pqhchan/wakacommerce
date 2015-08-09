
package com.wakacommerce.core.catalog.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.catalog.service.type.SkuFeeType;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupFee;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @ hui
 */
public interface SkuFee extends Serializable {
    
    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public Money getAmount();

    public void setAmount(Money amount);

    public Boolean getTaxable();

    public void setTaxable(Boolean taxable);

    public String getExpression();

    public void setExpression(String expression);
    
    public SkuFeeType getFeeType();

    public void setFeeType(SkuFeeType feeType);

    public List<Sku> getSkus();

    public void setSkus(List<Sku> skus);

    BroadleafCurrency getCurrency();

    void setCurrency(BroadleafCurrency currency);
    
}
