
package com.wakacommerce.core.offer.service.discount;

import java.io.Serializable;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferItemCriteria;
import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;

/**
 *
 * @ hui
 */
public class PromotionDiscount implements Serializable{ 
    private static final long serialVersionUID = 1L;
    
    private PromotableCandidateItemOffer candidateItemOffer;
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
    
    public PromotableCandidateItemOffer getCandidateItemOffer() {
        return candidateItemOffer;
    }

    public void setCandidateItemOffer(PromotableCandidateItemOffer candidateItemOffer) {
        this.candidateItemOffer = candidateItemOffer;
    }

    public PromotionDiscount split(int splitQty) {
        PromotionDiscount returnDiscount = copy();
        int originalQty = finalizedQuantity;

        setFinalizedQuantity(splitQty);
        setQuantity(splitQty);

        int newDiscountQty = originalQty - splitQty;
        if (newDiscountQty == 0) {
            return null;
        } else {
            returnDiscount.setQuantity(newDiscountQty);
            returnDiscount.setFinalizedQuantity(newDiscountQty);
        }
        return returnDiscount;
    }

    public PromotionDiscount copy() {
        PromotionDiscount pd = new PromotionDiscount();
        pd.setItemCriteria(itemCriteria);
        pd.setPromotion(promotion);
        pd.setQuantity(quantity);
        pd.setFinalizedQuantity(finalizedQuantity);
        pd.setCandidateItemOffer(candidateItemOffer);
        return pd;
    }
    
    public void resetQty(int qty) {
        quantity = qty;
        finalizedQuantity = qty;
    }
    
    public boolean isFinalized() {
        return quantity == finalizedQuantity;
    }

}
