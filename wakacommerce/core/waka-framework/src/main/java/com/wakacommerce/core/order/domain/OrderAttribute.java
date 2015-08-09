
package com.wakacommerce.core.order.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 *
 * @ hui
 */
public interface OrderAttribute extends Serializable, MultiTenantCloneable<OrderAttribute> {

    Long getId();

    void setId(Long id);

    String getValue();

    void setValue(String value);

    Order getOrder();

    void setOrder(Order order);

    String getName();

    void setName(String name);
}
