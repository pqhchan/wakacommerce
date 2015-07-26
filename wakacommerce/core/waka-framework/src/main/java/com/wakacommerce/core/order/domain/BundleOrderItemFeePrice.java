
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

import com.wakacommerce.common.money.Money;

public interface BundleOrderItemFeePrice extends Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract BundleOrderItem getBundleOrderItem();

    public abstract void setBundleOrderItem(BundleOrderItem bundleOrderItem);

    public abstract Money getAmount();

    public abstract void setAmount(Money amount);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract Boolean isTaxable();

    public abstract void setTaxable(Boolean isTaxable);

    public abstract String getReportingCode();

    public abstract void setReportingCode(String reportingCode);

    public BundleOrderItemFeePrice clone();

}
