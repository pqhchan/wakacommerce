
package com.wakacommerce.core.offer.service.discount;

import java.io.Serializable;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferItemCriteria;

/**
 *
 * @ hui
 */
public class PromotionQualifier implements Serializable{ 
    private static final long serialVersionUID = 1L;
    
    private Offer promotion;
    private OfferItemCriteria itemCriteria;
    private int quantity;
    private int finalizedQuantity;
    
    public Offer getPromotion() {
        return promotion;
    }
    public void setPromotion(Offer promotion) {
        this.promotion = promotion;
    }
    public OfferItemCriteria getItemCriteria() {
        return itemCriteria;
    }
    public void setItemCriteria(OfferItemCriteria itemCriteria) {
        this.itemCriteria = itemCriteria;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getFinalizedQuantity() {
        return finalizedQuantity;
    }
    public void setFinalizedQuantity(int finalizedQuantity) {
        this.finalizedQuantity = finalizedQuantity;
    }
    
    public void incrementQuantity(int quantity) {
        this.quantity = this.quantity + quantity;
    }
    
    public PromotionQualifier copy() {
        PromotionQualifier pq = new PromotionQualifier();
        pq.setItemCriteria(itemCriteria);
        pq.setPromotion(promotion);
        pq.setQuantity(quantity);
        pq.setFinalizedQuantity(finalizedQuantity);
        return pq;
    }
    
    public void resetQty(int qty) {
        quantity = qty;
        finalizedQuantity = qty;
    }

    public PromotionQualifier split(int splitItemQty) {
        PromotionQualifier returnQualifier = copy();
        int newQty = finalizedQuantity - splitItemQty;

        if (newQty <= 0) {
            throw new IllegalArgumentException("Splitting PromotionQualifier resulted in a negative quantity");
        }

        setFinalizedQuantity(newQty);
        setQuantity(newQty);

        returnQualifier.setQuantity(splitItemQty);
        returnQualifier.setFinalizedQuantity(splitItemQty);

        return returnQualifier;
    }

    public boolean isFinalized() {
        return quantity == finalizedQuantity;
    }

}
