
package com.wakacommerce.core.order.dao;

import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemPriceDetail;
import com.wakacommerce.core.order.domain.OrderItemQualifier;
import com.wakacommerce.core.order.domain.PersonalMessage;
import com.wakacommerce.core.order.service.type.OrderItemType;

public interface OrderItemDao {

    OrderItem readOrderItemById(Long orderItemId);

    OrderItem save(OrderItem orderItem);

    void delete(OrderItem orderItem);

    OrderItem create(OrderItemType orderItemType);

    OrderItem saveOrderItem(OrderItem orderItem);
    
    PersonalMessage createPersonalMessage();

    OrderItemPriceDetail createOrderItemPriceDetail();

    OrderItemQualifier createOrderItemQualifier();

    /**
     * Sets the initial orderItemPriceDetail for the item.
     */
    OrderItemPriceDetail initializeOrderItemPriceDetails(OrderItem item);

}
