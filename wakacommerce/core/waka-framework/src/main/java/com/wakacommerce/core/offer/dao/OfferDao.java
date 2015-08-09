
package com.wakacommerce.core.offer.dao;

import java.util.List;

import com.wakacommerce.core.offer.domain.CandidateFulfillmentGroupOffer;
import com.wakacommerce.core.offer.domain.CandidateItemOffer;
import com.wakacommerce.core.offer.domain.CandidateOrderOffer;
import com.wakacommerce.core.offer.domain.FulfillmentGroupAdjustment;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferInfo;
import com.wakacommerce.core.offer.domain.OrderAdjustment;
import com.wakacommerce.core.offer.domain.OrderItemAdjustment;
import com.wakacommerce.core.offer.domain.OrderItemPriceDetailAdjustment;

public interface OfferDao {

    List<Offer> readAllOffers();

    Offer readOfferById(Long offerId);

    List<Offer> readOffersByAutomaticDeliveryType();

    Offer save(Offer offer);

    void delete(Offer offer);

    Offer create();

    CandidateOrderOffer createCandidateOrderOffer();
    
    CandidateItemOffer createCandidateItemOffer();

    CandidateFulfillmentGroupOffer createCandidateFulfillmentGroupOffer();

    OrderItemAdjustment createOrderItemAdjustment();

    OrderItemPriceDetailAdjustment createOrderItemPriceDetailAdjustment();

    OrderAdjustment createOrderAdjustment();

    FulfillmentGroupAdjustment createFulfillmentGroupAdjustment();

    OfferInfo createOfferInfo();

    OfferInfo save(OfferInfo offerInfo);

    void delete(OfferInfo offerInfo);

    public Long getCurrentDateResolution();

    public void setCurrentDateResolution(Long currentDateResolution);
    
}
