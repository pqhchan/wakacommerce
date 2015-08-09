
package com.wakacommerce.core.checkout.service;

import com.wakacommerce.core.checkout.service.exception.CheckoutException;
import com.wakacommerce.core.checkout.service.workflow.CheckoutResponse;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.type.OrderStatus;

public interface CheckoutService {

    public CheckoutResponse performCheckout(Order order) throws CheckoutException;
    
}
