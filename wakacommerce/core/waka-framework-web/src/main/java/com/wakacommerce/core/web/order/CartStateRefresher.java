
package com.wakacommerce.core.web.order;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.core.order.domain.NullOrderImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderPersistedEntityListener;
import com.wakacommerce.core.order.domain.OrderPersistedEvent;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;


/**
 * {@link ApplicationListener} responsible for updating {@link CartState} with a new version that was persisted.
 * 
 *Phillip Verheyden (phillipuniverse)
 * 
 * @see {@link OrderPersistedEntityListener}
 * @see {@link OrderPersistedEvent}
 */
@Component("blCartStateRefresher")
public class CartStateRefresher implements ApplicationListener<OrderPersistedEvent> {

    /**
     * <p>Resets {@link CartState} with the newly persisted Order. If {@link CartState} was empty, this will only update it if
     * the {@link Order} that has been persisted is the {@link OrderStatus#IN_PROCESS} {@link Order} for the active
     * {@link Customer} (as determined by {@link CustomerState#getCustomer()}. If {@link CartState} was <b>not</b> empty,
     * then it will be replaced only if this newly persisted {@link Order} has the same id.</p>
     * 
     * <p>This ensures that whatever is returned from {@link CartState#getCart()} will always be the most up-to-date
     * database version (meaning, safe to write to the DB).</p>
     */
    @Override
    public void onApplicationEvent(final OrderPersistedEvent event) {
        WebRequest request = BroadleafRequestContext.getBroadleafRequestContext().getWebRequest();
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