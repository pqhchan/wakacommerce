
package com.wakacommerce.core.offer.service.processor;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;

/**
 * 
 *  
 *
 */
public interface BaseProcessor {
    
    public List<Offer> filterOffers(List<Offer> offers, Customer customer);
    
}
