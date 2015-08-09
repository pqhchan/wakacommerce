
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.OrderItemPriceDetailAdjustment;

import java.io.Serializable;
import java.util.List;

public interface OrderItemPriceDetail extends Serializable, MultiTenantCloneable<OrderItemPriceDetail> {

    Long getId();

    void setId(Long id);

    OrderItem getOrderItem();

    void setOrderItem(OrderItem order);

    List<OrderItemPriceDetailAdjustment> getOrderItemPriceDetailAdjustments();

    void setOrderItemAdjustments(List<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments);

    int getQuantity();

    void setQuantity(int quantity);

    Money getAdjustmentValue();

    Money getTotalAdjustmentValue();

    Money getTotalAdjustedPrice();

    boolean getUseSalePrice();

    void setUseSalePrice(boolean useSalePrice);

}
