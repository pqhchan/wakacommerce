
package com.wakacommerce.common.event;


/**
 *
 * @ hui
 */
public class OrderSubmittedEvent extends BroadleafApplicationEvent {

    private static final long serialVersionUID = 1L;

    protected final String orderNumber;

    public OrderSubmittedEvent(Long orderId, String orderNumber) {
        super(orderId);
        this.orderNumber = orderNumber;
    }

    public Long getOrderId() {
        return (Long) super.getSource();
    }

    public String getOrderNumber() {
        return (String) orderNumber;
    }
}
