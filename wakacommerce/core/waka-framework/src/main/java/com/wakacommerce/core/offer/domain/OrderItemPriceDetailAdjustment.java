
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.OrderItemPriceDetail;

/**
 *
 * @ hui
 */
public interface OrderItemPriceDetailAdjustment extends Adjustment, MultiTenantCloneable<OrderItemPriceDetailAdjustment> {

    public String getOfferName();

    public void setOfferName(String offerName);

    public OrderItemPriceDetail getOrderItemPriceDetail();

    public void init(OrderItemPriceDetail orderItemPriceDetail, Offer offer, String reason);

    public void setOrderItemPriceDetail(OrderItemPriceDetail orderItemPriceDetail);

    public boolean isAppliedToSalePrice();

    public void setAppliedToSalePrice(boolean appliedToSalePrice);

    public Money getRetailPriceValue();

    public void setRetailPriceValue(Money retailPriceValue);

    public Money getSalesPriceValue();

    public void setSalesPriceValue(Money salesPriceValue);
}
