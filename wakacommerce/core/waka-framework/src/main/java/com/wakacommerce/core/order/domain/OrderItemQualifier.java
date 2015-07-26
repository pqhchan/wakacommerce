
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

import com.wakacommerce.core.offer.domain.Offer;

public interface OrderItemQualifier extends Serializable {
    
    /**
     * Unique id of the item qualifier.
     * @return
     */
    Long getId();

    /**
     * Sets the id for this OrderItemQualifier
     * @param id
     */
    void setId(Long id);

    /**
     * The related order item.
     * @return
     */
    OrderItem getOrderItem();

    /**
     * Sets the related order item.
     * @param orderItem
     */
    void setOrderItem(OrderItem orderItem);

    /**
     * Sets the related offer.
     * @param offer
     */
    void setOffer(Offer offer);

    /**
     * Returns the related offer
     * @return
     */
    Offer getOffer();

    /**
     * Sets the quantity of the associated OrderItem that was used as a qualifier.
     * @param quantity
     */
    void setQuantity(Long quantity);

    /**
     * Returns the quantity of the associated OrderItem that was used as a qualifier.
     * @return
     */
    Long getQuantity();
}
