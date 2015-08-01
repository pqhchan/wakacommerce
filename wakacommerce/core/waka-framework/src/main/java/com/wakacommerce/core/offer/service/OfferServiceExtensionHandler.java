
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
 * ,  
 */
public interface OfferServiceExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType applyAdditionalFilters(List<Offer> offers);

    /**
     * Allows module extension to add additional offer codes to the list, given the customer
     * @param customer
     * @param offerCodes
     * @return
     */
    public ExtensionResultStatusType buildOfferCodeListForCustomer(Customer customer, List<OfferCode> offerCodes);

    /**
     * Modules may extend the calculatePotentialSavings method.   Once the handlers run, the 
     * contextMap will be checked for an entry with a key of "savings".    If that entry returns a 
     * non-null Money, that value will be returned from the calling method.
     * 
     * Otherwise, the map will be checked for an entry with a key of "quantity".   If a non-null Integer is
     * returned, that value will replace the quantity call in the normal call to calculatePotentialSavings.
     * 
     * This extension is utilized by one or more BLC enterprise modules including Subscription.
     * 
     * @param itemOffer
     * @param item
     * @param quantity
     * @param contextMap
     * @return
     */
    public ExtensionResultStatusType calculatePotentialSavings(PromotableCandidateItemOffer itemOffer,
            PromotableOrderItem item, int quantity, Map<String, Object> contextMap);

    /**
     * Modules may need to clear additional offer details when resetPriceDetails is called.
     * 
     * @param item
     * @return
     */
    public ExtensionResultStatusType resetPriceDetails(PromotableOrderItem item);

    /**
     * Modules may need to extend the applyItemOffer logic
     * 
     * For example, a subscription module might creates future payment adjustments.
     * 
     * The module add an attribute of type Boolean to the contextMap named "stopProcessing" indicating to 
     * the core offer engine that further adjustment processing is not needed. 
     * 
     * @param order
     * @param itemOffer
     * @param contextMap
     * @return
     */
    public ExtensionResultStatusType applyItemOffer(PromotableOrder order, PromotableCandidateItemOffer itemOffer,
            Map<String, Object> contextMap);

    /**
     * Allows a module to amend the data that synchronizes the {@link PromotableOrder} with the {@link Order}
     * @param order
     * @return
     */
    public ExtensionResultStatusType synchronizeAdjustmentsAndPrices(PromotableOrder order);

    /**
     * Allows a module to finalize adjustments.
     * @param order
     * @return
     */
    ExtensionResultStatusType chooseSaleOrRetailAdjustments(PromotableOrder order);

    /**
     * Allows module extensions to add a create a new instance of OrderItemPriceDetailAdjustment.  
     * The module should add the value to the resultHolder.getContextMap() with a key of "OrderItemPriceDetailAdjustment"
     * @param resultHolder
     * @return
     */
    ExtensionResultStatusType createOrderItemPriceDetailAdjustment(ExtensionResultHolder<?> resultHolder,
            OrderItemPriceDetail itemDetail);
}
