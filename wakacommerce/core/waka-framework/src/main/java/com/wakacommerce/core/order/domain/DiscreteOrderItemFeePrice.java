
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

import com.wakacommerce.common.money.Money;

public interface DiscreteOrderItemFeePrice extends Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    public DiscreteOrderItem getDiscreteOrderItem();

    public void setDiscreteOrderItem(DiscreteOrderItem discreteOrderItem);

    public abstract Money getAmount();

    public abstract void setAmount(Money amount);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getReportingCode();

    public abstract void setReportingCode(String reportingCode);

    public DiscreteOrderItemFeePrice clone();

}
