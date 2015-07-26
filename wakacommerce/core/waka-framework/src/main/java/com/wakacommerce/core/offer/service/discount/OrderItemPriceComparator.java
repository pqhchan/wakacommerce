
package com.wakacommerce.core.offer.service.discount;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrderItem;

import java.util.Comparator;

/**
 * 
 *jfischer
 *
 */
public class OrderItemPriceComparator implements Comparator<PromotableOrderItem> {
    
    private boolean applyToSalePrice = false;
    
    public OrderItemPriceComparator(boolean applyToSalePrice) {
        this.applyToSalePrice = applyToSalePrice;
    }

    public int compare(PromotableOrderItem c1, PromotableOrderItem c2) {
        
        Money price = c1.getPriceBeforeAdjustments(applyToSalePrice);
        Money price2 = c2.getPriceBeforeAdjustments(applyToSalePrice);
        
        // highest amount first
        return price2.compareTo(price);
    }

}
