
package com.wakacommerce.core.offer.service.discount;

import java.util.Comparator;

import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;

/**
 * 
 *  
 *
 */
public class ItemOfferComparator implements Comparator<PromotableCandidateItemOffer> {
    
    public static ItemOfferComparator INSTANCE = new ItemOfferComparator();

    public int compare(PromotableCandidateItemOffer p1, PromotableCandidateItemOffer p2) {
        
        Integer priority1 = p1.getPriority();
        Integer priority2 = p2.getPriority();
        
        int result = priority1.compareTo(priority2);
        
        if (result == 0) {
            // highest potential savings wins
            return p2.getPotentialSavings().compareTo(p1.getPotentialSavings());
        }
        return result;
    }

}
