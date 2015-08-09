
package com.wakacommerce.core.order.service.call;

/**
 *
 * @ hui
 */
public class OrderMultishipOptionDTO {

    protected Long id;
    protected Long orderItemId;
    protected Long addressId;
    protected Long fulfillmentOptionId;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderItemId() {
        return orderItemId;
    }
    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }
    public Long getAddressId() {
        return addressId;
    }
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    public Long getFulfillmentOptionId() {
        return fulfillmentOptionId;
    }
    public void setFulfillmentOptionId(Long fulfillmentOptionId) {
        this.fulfillmentOptionId = fulfillmentOptionId;
    }

}
