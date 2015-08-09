
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemContainer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface PromotableOrderItem extends Serializable {

    void updateRuleVariables(Map<String, Object> ruleVars);

    void resetPriceDetails();

    boolean isDiscountingAllowed();

    boolean isOrderItemContainer();

    OrderItemContainer getOrderItemContainer();

    Money getSalePriceBeforeAdjustments();

    Money getRetailPriceBeforeAdjustments();

    boolean isOnSale();

    List<PromotableOrderItemPriceDetail> getPromotableOrderItemPriceDetails();

    Money getPriceBeforeAdjustments(boolean applyToSalePrice);

    Money getCurrentBasePrice();

    int getQuantity();

    BroadleafCurrency getCurrency();

    void removeAllItemAdjustments();

    void mergeLikeDetails();

    Long getOrderItemId();

    Money calculateTotalAdjustmentValue();

    Money calculateTotalWithAdjustments();

    Money calculateTotalWithoutAdjustments();

    PromotableOrderItemPriceDetail createNewDetail(int quantity);

    OrderItem getOrderItem();

    Map<String, Object> getExtraDataMap();
}
