
package com.wakacommerce.core.web.order;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.web.order.security.CartStateRequestProcessor;

@Component("blCartState")
public class CartState {

    /**
     * Gets the current cart based on the current request
     * 
     * @return the current customer's cart
     */
    public static Order getCart() {
        if (BroadleafRequestContext.getBroadleafRequestContext() == null ||
                BroadleafRequestContext.getBroadleafRequestContext().getWebRequest() == null) {
            return null;
        }

        WebRequest request = BroadleafRequestContext.getBroadleafRequestContext().getWebRequest();
        return (Order) request.getAttribute(CartStateRequestProcessor.getCartRequestAttributeName(), WebRequest.SCOPE_REQUEST);
    }
    
    /**
     * Sets the current cart on the current request
     * 
     * @param cart the new cart to set
     */
    public static void setCart(Order cart) {
        WebRequest request = BroadleafRequestContext.getBroadleafRequestContext().getWebRequest();
        request.setAttribute(CartStateRequestProcessor.getCartRequestAttributeName(), cart, WebRequest.SCOPE_REQUEST);
    }

}
