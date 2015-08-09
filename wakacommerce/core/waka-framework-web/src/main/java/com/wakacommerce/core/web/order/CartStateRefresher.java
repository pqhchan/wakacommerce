
package com.wakacommerce.core.web.order;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.order.domain.NullOrderImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderPersistedEntityListener;
import com.wakacommerce.core.order.domain.OrderPersistedEvent;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;


/**
 *
 * @ hui
 */
@Component("blCartStateRefresher")
public class CartStateRefresher implements ApplicationListener<OrderPersistedEvent> {

    @Override
    public void onApplicationEvent(final OrderPersistedEvent event) {
        WebRequest request = WakaRequestContext.getWakaRequestContext().getWebRequest();
        if (request != null) {
             Order dbOrder = event.getOrder();
            //Update the cart state ONLY IF the IDs of the newly persisted order and whatever is already in CartState match
            boolean emptyCartState = CartState.getCart() == null || CartState.getCart() instanceof NullOrderImpl;
            if (emptyCartState) {
                //If cart state is empty, set it to this newly persisted order if it's the active Customer's cart
                if (CustomerState.getCustomer() != null && CustomerState.getCustomer().getId().equals(dbOrder.getCustomer().getId())
                        && OrderStatus.IN_PROCESS.equals(dbOrder.getStatus())) {
                    CartState.setCart(dbOrder);
                }
            } else if (CartState.getCart().getId().equals(dbOrder.getId())) {
                CartState.setCart(dbOrder);
            }
        }
    }

}
