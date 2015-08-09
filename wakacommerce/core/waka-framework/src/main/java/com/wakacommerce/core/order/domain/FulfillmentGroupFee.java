
package com.wakacommerce.core.order.domain;

import java.io.Serializable;
import java.util.List;

import com.wakacommerce.common.money.Money;

public interface FulfillmentGroupFee extends Serializable {

    public Long getId();

    public void setId(Long id);

    public FulfillmentGroup getFulfillmentGroup();

    public void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

    public Money getAmount();

    public void setAmount(Money amount);

    public String getName();

    public void setName(String name);

    public String getReportingCode();

    public void setReportingCode(String reportingCode);

    public Boolean isTaxable();

    public void setTaxable(Boolean taxable);

    public List<TaxDetail> getTaxes();

    public void setTaxes(List<TaxDetail> taxes);

    public Money getTotalTax();

    public void setTotalTax(Money totalTax);
}
