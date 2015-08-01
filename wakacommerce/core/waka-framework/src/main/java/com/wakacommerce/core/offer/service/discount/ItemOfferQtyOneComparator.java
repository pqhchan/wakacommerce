
package com.wakacommerce.core.offer.service.discount;

import java.util.Comparator;

import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;

/**
 * The same as {@link ItemOfferComparator} but uses the {@link PromotableCandidateItemOffer#getPotentialSavingsQtyOne()}
 * method instead.
 * 
 * 
 */
public class ItemOfferQtyOneComparator implements Comparator<PromotableCandidateItemOffer> {
    
    public static ItemOfferQtyOneComparator INSTANCE = new ItemOfferQtyOneComparator();

    public int compare(PromotableCandidateItemOffer p1, PromotableCandidateItemOffer p2) {
        
        Integer priority1 = p1.getPriority();
        Integer priority2 = p2.getPriority();
        
        int result = priority1.compareTo(priority2);
        
        if (result == 0) {
            // highest potential savings wins
            return p2.getPotentialSavingsQtyOne().compareTo(p1.getPotentialSavingsQtyOne());
        }
        return result;
    }

}
