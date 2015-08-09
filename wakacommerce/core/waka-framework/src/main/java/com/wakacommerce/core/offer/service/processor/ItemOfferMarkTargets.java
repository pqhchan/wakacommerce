
package com.wakacommerce.core.offer.service.processor;

import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrder;
import com.wakacommerce.core.order.domain.OrderItem;


/**
 *
 * @ hui
 */
public interface ItemOfferMarkTargets {

    boolean markTargets(PromotableCandidateItemOffer itemOffer, PromotableOrder order, OrderItem relatedQualifier,
            boolean checkOnly);
}
