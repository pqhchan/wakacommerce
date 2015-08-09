
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

import com.wakacommerce.core.offer.domain.Offer;

public interface OrderItemQualifier extends Serializable {

    Long getId();

    void setId(Long id);

    OrderItem getOrderItem();

    void setOrderItem(OrderItem orderItem);

    void setOffer(Offer offer);

    Offer getOffer();

    void setQuantity(Long quantity);

    Long getQuantity();
}
