
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.ValueAssignable;

/**
 *
 * @ hui
 */
public interface OrderItemAttribute extends ValueAssignable<String>, MultiTenantCloneable<OrderItemAttribute> {

    Long getId();

    void setId(Long id);

    OrderItem getOrderItem();

    void setOrderItem(OrderItem orderItem);

    public OrderItemAttribute clone();
}
