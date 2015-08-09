
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.service.type.FulfillmentGroupStatusType;

import java.io.Serializable;
import java.util.List;

public interface FulfillmentGroupItem extends Serializable {

    public Long getId();

    public void setId(Long id);

    public FulfillmentGroup getFulfillmentGroup();

    public void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

    public OrderItem getOrderItem();

    public void setOrderItem(OrderItem orderItem);

    public int getQuantity();

    public void setQuantity(int quantity);

    public Money getRetailPrice();

    public Money getSalePrice();

    public Money getPrice();
    
    public Money getTotalItemAmount();

    public void setTotalItemAmount(Money amount);
    
    public Money getProratedOrderAdjustmentAmount();

    public void setProratedOrderAdjustmentAmount(Money amount);

    public Money getTotalItemTaxableAmount();

    public void setTotalItemTaxableAmount(Money amount);    

    public FulfillmentGroupStatusType getStatus();

    public void setStatus(FulfillmentGroupStatusType status);
    
    public void removeAssociations();

    public FulfillmentGroupItem clone();

    public List<TaxDetail> getTaxes();

    public void setTaxes(List<TaxDetail> taxes);

    public Money getTotalTax();

    public void setTotalTax(Money totalTax);

    boolean getHasProratedOrderAdjustments();

}
