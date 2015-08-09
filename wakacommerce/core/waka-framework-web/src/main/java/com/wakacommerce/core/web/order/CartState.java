
package com.wakacommerce.core.web.order;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.web.order.security.CartStateRequestProcessor;

@Component("blCartState")
public class CartState {

    public static Order getCart() {
        if (WakaRequestContext.getWakaRequestContext() == null ||
                WakaRequestContext.getWakaRequestContext().getWebRequest() == null) {
            return null;
        }

        WebRequest request = WakaRequestContext.getWakaRequestContext().getWebRequest();
        return (Order) request.getAttribute(CartStateRequestProcessor.getCartRequestAttributeName(), WebRequest.SCOPE_REQUEST);
    }

    public static void setCart(Order cart) {
        WebRequest request = WakaRequestContext.getWakaRequestContext().getWebRequest();
        request.setAttribute(CartStateRequestProcessor.getCartRequestAttributeName(), cart, WebRequest.SCOPE_REQUEST);
    }

}
