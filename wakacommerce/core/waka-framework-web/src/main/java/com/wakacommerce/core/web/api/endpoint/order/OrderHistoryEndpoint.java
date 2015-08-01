
package com.wakacommerce.core.web.api.endpoint.order;

import org.springframework.http.HttpStatus;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.type.OrderStatus;
import com.wakacommerce.core.web.api.BroadleafWebServicesException;
import com.wakacommerce.core.web.api.endpoint.BaseEndpoint;
import com.wakacommerce.core.web.api.wrapper.OrderWrapper;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * This endpoint depends on JAX-RS.  It should be extended by components that actually wish 
 * to provide an endpoint.  The annotations such as @Path, @Scope, @Context, @PathParam, @QueryParam, 
 * @GET, @POST, @PUT, and @DELETE are purposely not provided here to allow implementors finer control over 
 * the details of the endpoint.
 * <p/>
 * User:   
 * Date: 4/10/12
 */
public abstract class OrderHistoryEndpoint extends BaseEndpoint {

    @Resource(name="blOrderService")
    protected OrderService orderService;

    public List<OrderWrapper> findOrdersForCustomer(HttpServletRequest request,
            String orderStatus) {
        Customer customer = CustomerState.getCustomer(request);
        OrderStatus status = OrderStatus.getInstance(orderStatus);

        if (customer != null && status != null) {
            List<Order> orders = orderService.findOrdersForCustomer(customer, status);

            if (orders != null && !orders.isEmpty()) {
                List<OrderWrapper> wrappers = new ArrayList<OrderWrapper>();
                for (Order order : orders) {
                    OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
                    wrapper.wrapSummary(order, request);
                    wrappers.add(wrapper);
                }

                return wrappers;
            }

            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                    .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }

        throw BroadleafWebServicesException.build(HttpStatus.BAD_REQUEST.value())
                .addMessage(BroadleafWebServicesException.CUSTOMER_NOT_FOUND);
    }
}
