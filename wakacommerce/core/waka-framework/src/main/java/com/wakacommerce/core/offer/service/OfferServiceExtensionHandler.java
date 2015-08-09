
package com.wakacommerce.core.offer.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrder;
import com.wakacommerce.core.offer.service.discount.domain.PromotableOrderItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItemPriceDetail;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;
import java.util.Map;


/**
 *
 * @ hui
 */
public interface OfferServiceExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType applyAdditionalFilters(List<Offer> offers);

    public ExtensionResultStatusType buildOfferCodeListForCustomer(Customer customer, List<OfferCode> offerCodes);

    public ExtensionResultStatusType calculatePotentialSavings(PromotableCandidateItemOffer itemOffer,
            PromotableOrderItem item, int quantity, Map<String, Object> contextMap);

    public ExtensionResultStatusType resetPriceDetails(PromotableOrderItem item);

    public ExtensionResultStatusType applyItemOffer(PromotableOrder order, PromotableCandidateItemOffer itemOffer,
            Map<String, Object> contextMap);

    public ExtensionResultStatusType synchronizeAdjustmentsAndPrices(PromotableOrder order);

    ExtensionResultStatusType chooseSaleOrRetailAdjustments(PromotableOrder order);

    ExtensionResultStatusType createOrderItemPriceDetailAdjustment(ExtensionResultHolder<?> resultHolder,
            OrderItemPriceDetail itemDetail);
}
