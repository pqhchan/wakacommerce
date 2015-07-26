
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

    /**
     * Returns the number of milliseconds that the current date/time will be cached for queries before refreshing.
     * This aids in query caching, otherwise every query that utilized current date would be different and caching
     * would be ineffective.
     *
     * @return the milliseconds to cache the current date/time
     */
    public Long getCurrentDateResolution();

    /**
     * Sets the number of milliseconds that the current date/time will be cached for queries before refreshing.
     * This aids in query caching, otherwise every query that utilized current date would be different and caching
     * would be ineffective.
     *
     * @param currentDateResolution the milliseconds to cache the current date/time
     */
    public void setCurrentDateResolution(Long currentDateResolution);
    
}
