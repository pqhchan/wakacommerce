
package com.wakacommerce.core.order.domain;

import com.wakacommerce.profile.core.domain.Address;

/**
 *
 * @ hui
 */
public interface OrderMultishipOption {

    public Long getId();

    public void setId(Long id);

    public Order getOrder();

    public void setOrder(Order order);

    public OrderItem getOrderItem();

    public void setOrderItem(OrderItem orderItem);

    public Address getAddress();

    public void setAddress(Address address);

    public FulfillmentOption getFulfillmentOption();

    public void setFulfillmentOption(FulfillmentOption fulfillmentOption);

}
