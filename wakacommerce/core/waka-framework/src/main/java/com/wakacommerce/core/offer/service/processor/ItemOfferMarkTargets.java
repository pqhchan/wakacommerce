
package com.wakacommerce.core.offer.service.processor;

import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrder;
import com.wakacommerce.core.order.domain.OrderItem;


/**
 * This interface is used as a part of a template pattern in ItemOfferProcessor that allows reuse to other BLC modules.
 * 
 * Changes here likely affect Subscription and AdvancedOffer modules.
 * 
 *
 */
public interface ItemOfferMarkTargets {

    boolean markTargets(PromotableCandidateItemOffer itemOffer, PromotableOrder order, OrderItem relatedQualifier,
            boolean checkOnly);
}
