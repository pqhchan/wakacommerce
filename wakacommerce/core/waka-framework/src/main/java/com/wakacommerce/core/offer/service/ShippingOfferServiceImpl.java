
package com.wakacommerce.core.offer.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.PricingException;

import java.util.List;

import javax.annotation.Resource;

/**
 * 
 *  
 *
 */
@Service("blShippingOfferService")
public class ShippingOfferServiceImpl implements ShippingOfferService {
    
    @Resource(name="blOfferService")
    protected OfferService offerService;

    @Override
    public void reviewOffers(Order order) throws PricingException {
        List<Offer> offers = offerService.buildOfferListForOrder(order);
        offerService.applyAndSaveFulfillmentGroupOffersToOrder(offers, order);
    }

}
