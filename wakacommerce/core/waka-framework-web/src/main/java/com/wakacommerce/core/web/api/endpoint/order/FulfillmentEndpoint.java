
package com.wakacommerce.core.web.api.endpoint.order;

import org.springframework.http.HttpStatus;

import com.wakacommerce.core.checkout.service.CheckoutService;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.FulfillmentGroupService;
import com.wakacommerce.core.order.service.FulfillmentOptionService;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.call.FulfillmentGroupItemRequest;
import com.wakacommerce.core.order.service.call.FulfillmentGroupRequest;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.api.BroadleafWebServicesException;
import com.wakacommerce.core.web.api.endpoint.BaseEndpoint;
import com.wakacommerce.core.web.api.wrapper.FulfillmentGroupItemWrapper;
import com.wakacommerce.core.web.api.wrapper.FulfillmentGroupWrapper;
import com.wakacommerce.core.web.api.wrapper.FulfillmentOptionWrapper;
import com.wakacommerce.core.web.api.wrapper.OrderWrapper;
import com.wakacommerce.core.web.order.CartState;

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
public abstract class FulfillmentEndpoint extends BaseEndpoint {

    @Resource(name="blCheckoutService")
    protected CheckoutService checkoutService;

    @Resource(name="blOrderService")
    protected OrderService orderService;
    
    @Resource(name="blFulfillmentGroupService")
    protected FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "blFulfillmentOptionService")
    protected FulfillmentOptionService fulfillmentOptionService;

    public List<FulfillmentGroupWrapper> findFulfillmentGroupsForOrder(HttpServletRequest request) {
        Order cart = CartState.getCart();
        if (cart != null && cart.getFulfillmentGroups() != null && !cart.getFulfillmentGroups().isEmpty()) {
            List<FulfillmentGroup> fulfillmentGroups = cart.getFulfillmentGroups();
            List<FulfillmentGroupWrapper> fulfillmentGroupWrappers = new ArrayList<FulfillmentGroupWrapper>();
            for (FulfillmentGroup fulfillmentGroup : fulfillmentGroups) {
                FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                fulfillmentGroupWrapper.wrapSummary(fulfillmentGroup, request);
                fulfillmentGroupWrappers.add(fulfillmentGroupWrapper);
            }
            return fulfillmentGroupWrappers;
        }
        throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
    }

    public OrderWrapper removeAllFulfillmentGroupsFromOrder(HttpServletRequest request,
            boolean priceOrder) {
        Order cart = CartState.getCart();
        if (cart != null) {
            try {
                fulfillmentGroupService.removeAllFulfillmentGroupsFromOrder(cart, priceOrder);
                OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
                wrapper.wrapDetails(cart, request);
                return wrapper;
            } catch (PricingException e) {
                throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                        .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
            }
        }
        throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);

    }

    public FulfillmentGroupWrapper addFulfillmentGroupToOrder(HttpServletRequest request,
            FulfillmentGroupWrapper wrapper,
            boolean priceOrder) {
        Order cart = CartState.getCart();
        if (cart != null) {
            FulfillmentGroupRequest fulfillmentGroupRequest = wrapper.unwrap(request, context);

            if (fulfillmentGroupRequest.getOrder() != null && fulfillmentGroupRequest.getOrder().getId().equals(cart.getId())) {
                try {
                    fulfillmentGroupRequest.setOrder(cart);
                    FulfillmentGroup fulfillmentGroup = fulfillmentGroupService.addFulfillmentGroupToOrder(fulfillmentGroupRequest, priceOrder);
                    FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                    fulfillmentGroupWrapper.wrapDetails(fulfillmentGroup, request);
                    return fulfillmentGroupWrapper;
                } catch (PricingException e) {
                    throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                            .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
                }
            }
        }
        throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);

    }

    public FulfillmentGroupWrapper addItemToFulfillmentGroup(HttpServletRequest request,
            Long fulfillmentGroupId,
            FulfillmentGroupItemWrapper wrapper,
            boolean priceOrder) {
        Order cart = CartState.getCart();
        if (cart != null) {
            FulfillmentGroupItemRequest fulfillmentGroupItemRequest = wrapper.unwrap(request, context);
            if (fulfillmentGroupItemRequest.getOrderItem() != null) {
                FulfillmentGroup fulfillmentGroup = null;
                OrderItem orderItem = null;

                for (FulfillmentGroup fg : cart.getFulfillmentGroups()) {
                    if (fg.getId().equals(fulfillmentGroupId)) {
                        fulfillmentGroup = fg;
                    }
                }
                fulfillmentGroupItemRequest.setFulfillmentGroup(fulfillmentGroup);

                for (OrderItem oi : cart.getOrderItems()) {
                    if (oi.getId().equals(fulfillmentGroupItemRequest.getOrderItem().getId())) {
                        orderItem = oi;
                    }
                }
                fulfillmentGroupItemRequest.setOrderItem(orderItem);

                if (fulfillmentGroup != null && orderItem != null) {
                    try {
                        FulfillmentGroup fg = fulfillmentGroupService.addItemToFulfillmentGroup(fulfillmentGroupItemRequest, priceOrder);
                        FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                        fulfillmentGroupWrapper.wrapDetails(fg, request);
                        return fulfillmentGroupWrapper;

                    } catch (PricingException e) {
                        throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                                .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
                    }
                }
            }
        }
        throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);

    }

    public FulfillmentGroupWrapper addFulfillmentOptionToFulfillmentGroup(HttpServletRequest request,
            Long fulfillmentGroupId,
            Long fulfillmentOptionId,
            boolean priceOrder) {

        FulfillmentOption option = fulfillmentOptionService.readFulfillmentOptionById(fulfillmentOptionId);
        if (option == null) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                    .addMessage(BroadleafWebServicesException.FULFILLMENT_OPTION_NOT_FOUND, fulfillmentOptionId);
        }

        Order cart = CartState.getCart();
        if (cart != null) {
            boolean found = false;
            List<FulfillmentGroup> groups = cart.getFulfillmentGroups();
            if (groups != null && !groups.isEmpty()) {
                for (FulfillmentGroup group : groups) {
                    if (group.getId().equals(fulfillmentGroupId)) {
                        group.setFulfillmentOption(option);
                        found = true;
                        break;
                    }
                }
            }
            try {
                if (found) {
                    cart = orderService.save(cart, priceOrder);
                    for (FulfillmentGroup fg : groups) {
                        if (fg.getId().equals(fulfillmentGroupId)) {
                            FulfillmentGroupWrapper fulfillmentGroupWrapper = (FulfillmentGroupWrapper) context.getBean(FulfillmentGroupWrapper.class.getName());
                            fulfillmentGroupWrapper.wrapDetails(fg, request);
                            return fulfillmentGroupWrapper;
                        }
                    }
                } else {
                    throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                            .addMessage(BroadleafWebServicesException.FULFILLMENT_GROUP_NOT_FOUND, fulfillmentGroupId);
                }
            } catch (PricingException e) {
                throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                        .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
            }
        }
        throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
    }

    public List<FulfillmentOptionWrapper> findFulfillmentOptions(HttpServletRequest request, String fulfillmentType) {
        ArrayList<FulfillmentOptionWrapper> out = new ArrayList<FulfillmentOptionWrapper>();
        List<FulfillmentOption> options = null;
        FulfillmentType type = FulfillmentType.getInstance(fulfillmentType);
        if (type != null) {
            options = fulfillmentOptionService.readAllFulfillmentOptionsByFulfillmentType(type);
        } else {
            options = fulfillmentOptionService.readAllFulfillmentOptions();
        }
        
        for (FulfillmentOption option : options) {
            FulfillmentOptionWrapper fulfillmentOptionWrapper = (FulfillmentOptionWrapper) context.getBean(FulfillmentOptionWrapper.class.getName());
            fulfillmentOptionWrapper.wrapDetails(option, request);
            out.add(fulfillmentOptionWrapper);
        }
        
        return out;
    }
}
