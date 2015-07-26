
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemContainer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface PromotableOrderItem extends Serializable {
    
    /**
     * Adds the item to the rule variables map.
     * @param ruleVars
     */
    void updateRuleVariables(Map<String, Object> ruleVars);

    /**
     * Called by pricing engine to reset the state of this item.    
     */
    void resetPriceDetails();

    /**
     * Returns true if this item can receive item level discounts.
     * @return
     */
    boolean isDiscountingAllowed();

    /**
     * Returns true if this PromotableOrderItem contains other items    
     */
    boolean isOrderItemContainer();

    /**
     * Returns an OrderItemContainer for this OrderItem or null if this item is not 
     * an instance of OrderItemContainer.  
     */
    OrderItemContainer getOrderItemContainer();

    /**
     * Returns the salePrice without adjustments
     */
    Money getSalePriceBeforeAdjustments();

    /**
     * Returns the retailPrice without adjustments
     */
    Money getRetailPriceBeforeAdjustments();

    /**
     * Returns true if the item has a sale price that is lower than the retail price.
     */
    boolean isOnSale();

    /**
     * Returns the list of priceDetails associated with this item.
     * @return
     */
    List<PromotableOrderItemPriceDetail> getPromotableOrderItemPriceDetails();

    /**
     * Return the salePriceBeforeAdjustments if the passed in param is true.
     * Otherwise return the retailPriceBeforeAdjustments.
     * @return
     */
    Money getPriceBeforeAdjustments(boolean applyToSalePrice);

    /**
     * Returns the basePrice of the item (baseSalePrice or baseRetailPrice)
     * @return
     */
    Money getCurrentBasePrice();

    /**
     * Returns the quantity for this orderItem
     * @return
     */
    int getQuantity();

    /**
     * Returns the currency of the related order.
     * @return
     */
    BroadleafCurrency getCurrency();

    /**
     * Effectively deletes all priceDetails associated with this item and r
     */
    void removeAllItemAdjustments();

    /**
     * Merges any priceDetails that share the same adjustments.
     */
    void mergeLikeDetails();

    /**
     * Returns the id of the contained OrderItem
     */
    Long getOrderItemId();

    /**
     * Returns the value of all adjustments.
     */
    Money calculateTotalAdjustmentValue();

    /**
     * Returns the final total for this item taking into account the finalized
     * adjustments.    Intended to be called after the adjustments have been 
     * finalized.
     */
    Money calculateTotalWithAdjustments();

    /**
     * Returns the total for this item if not adjustments applied.
     */
    Money calculateTotalWithoutAdjustments();

    /**
     * Creates a new detail with the associated quantity.   Intended for use as part of the PriceDetail split.
     * @param quantity
     * @return
     */
    PromotableOrderItemPriceDetail createNewDetail(int quantity);

    /**
     * Returns the underlying orderItem.    Manipulation of the underlying orderItem is not recommended.
     * This method is intended for unit test and read only access although that is not strictly enforced.    
     * @return
     */
    OrderItem getOrderItem();

    /**
     * Map available to implementations to store data needed for custom logic.
     */
    Map<String, Object> getExtraDataMap();
}
