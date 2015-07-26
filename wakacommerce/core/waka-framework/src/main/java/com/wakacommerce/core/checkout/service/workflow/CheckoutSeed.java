
package com.wakacommerce.core.checkout.service.workflow;

import java.util.Map;

import com.wakacommerce.core.order.domain.Order;

public class CheckoutSeed implements CheckoutResponse {

    protected Order order;
    protected final Map<String, Object> userDefinedFields;

    public CheckoutSeed(Order order, Map<String, Object> userDefinedFields) {
        this.order = order;
        this.userDefinedFields = userDefinedFields;
    }

    @Override
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }

    public Map<String, Object> getUserDefinedFields() {
        return userDefinedFields;
    }
}
