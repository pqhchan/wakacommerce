
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.OrderItem;

public interface OrderItemAdjustment extends Adjustment {

    public OrderItem getOrderItem();

    public void init(OrderItem orderItem, Offer offer, String reason);

    public void setOrderItem(OrderItem orderItem);

    public boolean isAppliedToSalePrice();

    public void setAppliedToSalePrice(boolean appliedToSalePrice);

    public Money getRetailPriceValue();

    public void setRetailPriceValue(Money retailPriceValue);

    public Money getSalesPriceValue();

    public void setSalesPriceValue(Money salesPriceValue);
}
