
package com.wakacommerce.core.web.api.endpoint.order;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.OfferService;
import com.wakacommerce.core.offer.service.exception.OfferAlreadyAddedException;
import com.wakacommerce.core.offer.service.exception.OfferException;
import com.wakacommerce.core.offer.service.exception.OfferExpiredException;
import com.wakacommerce.core.offer.service.exception.OfferMaxUseExceededException;
import com.wakacommerce.core.order.domain.NullOrderImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.core.order.service.exception.AddToCartException;
import com.wakacommerce.core.order.service.exception.ItemNotFoundException;
import com.wakacommerce.core.order.service.exception.RemoveFromCartException;
import com.wakacommerce.core.order.service.exception.UpdateCartException;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.api.BroadleafWebServicesException;
import com.wakacommerce.core.web.api.endpoint.BaseEndpoint;
import com.wakacommerce.core.web.api.endpoint.catalog.CatalogEndpoint;
import com.wakacommerce.core.web.api.wrapper.OrderWrapper;
import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.web.core.CustomerState;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public abstract class CartEndpoint extends BaseEndpoint {

    @Resource(name="blOrderService")
    protected OrderService orderService;

    @Resource(name="blOfferService")
    protected OfferService offerService;

    @Resource(name="blCustomerService")
    protected CustomerService customerService;

    public OrderWrapper findCartForCustomer(HttpServletRequest request) {
        Order cart = CartState.getCart();
        if (cart == null || cart instanceof NullOrderImpl) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }
        
        OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
        wrapper.wrapDetails(cart, request);

        return wrapper;
    }

    public OrderWrapper createNewCartForCustomer(HttpServletRequest request) {
        Customer customer = CustomerState.getCustomer(request);

        if (customer == null) {
            customer = customerService.createCustomerFromId(null);
        }

        Order cart = orderService.findCartForCustomer(customer);
        if (cart == null) {
            cart = orderService.createNewCartForCustomer(customer);
            CartState.setCart(cart);
        }

        OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
        wrapper.wrapDetails(cart, request);

        return wrapper;
    }

    public OrderWrapper addProductToOrder(HttpServletRequest request,
            MultiValueMap<String, String> requestParams,
            Long productId,
            Long categoryId,
            int quantity,
            boolean priceOrder) {
        
        Order cart = CartState.getCart();
        if (cart == null || cart instanceof NullOrderImpl) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }

        try {
            //We allow product options to be submitted via form post or via query params.  We need to take 
            //the product options and build a map with them...
            HashMap<String, String> productOptions = getOptions(requestParams);

            OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
            orderItemRequestDTO.setProductId(productId);
            orderItemRequestDTO.setCategoryId(categoryId);
            orderItemRequestDTO.setQuantity(quantity);

            //If we have product options set them on the DTO
            if (productOptions.size() > 0) {
                orderItemRequestDTO.setItemAttributes(productOptions);
            }

            Order order = orderService.addItem(cart.getId(), orderItemRequestDTO, priceOrder);
            order = orderService.save(order, priceOrder);

            OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
            wrapper.wrapDetails(order, request);

            return wrapper;
        } catch (PricingException e) {
            throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e);
        } catch (AddToCartException e) {
            throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e);
        }
    }

    protected HashMap<String, String> getOptions(MultiValueMap<String, String> requestParams) {
        HashMap<String, String> productOptions = new HashMap<String, String>();

        //Fill up a map of key values that will represent product options
        Set<String> keySet = requestParams.keySet();
        for (String key : keySet) {
            if (requestParams.getFirst(key) != null) {
                //Product options should be returned with "productOption." as a prefix. We'll look for those, and 
                //remove the prefix.
                if (key.startsWith("productOption.")) {
                    productOptions.put(StringUtils.removeStart(key, "productOption."), requestParams.getFirst(key));
                }
            }
        }
        return productOptions;
    }

    public OrderWrapper removeItemFromOrder(HttpServletRequest request,
            Long itemId,
            boolean priceOrder) {

        Order cart = CartState.getCart();
        if (cart == null || cart instanceof NullOrderImpl) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }
        
        try {
            Order order = orderService.removeItem(cart.getId(), itemId, priceOrder);
            order = orderService.save(order, priceOrder);

            OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
            wrapper.wrapDetails(order, request);

            return wrapper;
        } catch (PricingException e) {
            throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                    .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
        } catch (RemoveFromCartException e) {
            if (e.getCause() instanceof ItemNotFoundException) {
                throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value(), null, null, e.getCause())
                        .addMessage(BroadleafWebServicesException.CART_ITEM_NOT_FOUND, itemId);
            } else {
                throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e);
            }
        }
    }

    public OrderWrapper updateItemQuantity(HttpServletRequest request,
            Long itemId,
            Integer quantity,
            boolean priceOrder) {

        Order cart = CartState.getCart();

        if (cart == null || cart instanceof NullOrderImpl) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }
        
        try {
                OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
                orderItemRequestDTO.setOrderItemId(itemId);
                orderItemRequestDTO.setQuantity(quantity);
                Order order = orderService.updateItemQuantity(cart.getId(), orderItemRequestDTO, priceOrder);
                order = orderService.save(order, priceOrder);

                OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
                wrapper.wrapDetails(order, request);

                return wrapper;
        } catch (UpdateCartException e) {
            if (e.getCause() instanceof ItemNotFoundException) {
                throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value(), null, null, e.getCause())
                        .addMessage(BroadleafWebServicesException.CART_ITEM_NOT_FOUND, itemId);
            } else {
                throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                        .addMessage(BroadleafWebServicesException.UPDATE_CART_ERROR);
            }
        } catch (RemoveFromCartException e) {
            if (e.getCause() instanceof ItemNotFoundException) {
                throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value(), null, null, e.getCause())
                        .addMessage(BroadleafWebServicesException.CART_ITEM_NOT_FOUND, itemId);
            } else {
                throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                        .addMessage(BroadleafWebServicesException.UPDATE_CART_ERROR);
            }
        } catch (PricingException pe) {
           throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, pe)
                    .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
        }
    }

    public OrderWrapper addOfferCode(HttpServletRequest request,
            String promoCode,
            boolean priceOrder) {

        Order cart = CartState.getCart();

        if (cart == null || cart instanceof NullOrderImpl) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                    .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }

        OfferCode offerCode = offerService.lookupOfferCodeByCode(promoCode);

        if (offerCode == null) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                    .addMessage(BroadleafWebServicesException.PROMO_CODE_INVALID, promoCode);
        }

        try {
            cart = orderService.addOfferCode(cart, offerCode, priceOrder);
            OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
            wrapper.wrapDetails(cart, request);

            return wrapper;
        } catch (PricingException e) {
            throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                    .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
        } catch (OfferException e) {
            Throwable t = e.getCause();
            if (t instanceof OfferMaxUseExceededException) {
                throw BroadleafWebServicesException.build(HttpStatus.BAD_REQUEST.value(), null, null, e)
                .addMessage(BroadleafWebServicesException.PROMO_CODE_MAX_USAGES, promoCode);
            } else if (t instanceof OfferExpiredException) {
                throw BroadleafWebServicesException.build(HttpStatus.BAD_REQUEST.value(), null, null, e)
                .addMessage(BroadleafWebServicesException.PROMO_CODE_EXPIRED, promoCode);
            } else if (t instanceof OfferAlreadyAddedException) {
                throw BroadleafWebServicesException.build(HttpStatus.BAD_REQUEST.value(), null, null, e)
                .addMessage(BroadleafWebServicesException.PROMO_CODE_ALREADY_ADDED, promoCode);
            } else {
                throw BroadleafWebServicesException.build(HttpStatus.BAD_REQUEST.value(), null, null, e)
                .addMessage(BroadleafWebServicesException.PROMO_CODE_INVALID, promoCode);
            }
        }
    }

    public OrderWrapper removeOfferCode(HttpServletRequest request,
            String promoCode,
            boolean priceOrder) {
        Order cart = CartState.getCart();
        if (cart == null || cart instanceof NullOrderImpl) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                    .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }

        OfferCode offerCode = offerService.lookupOfferCodeByCode(promoCode);
        if (offerCode == null) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                    .addMessage(BroadleafWebServicesException.PROMO_CODE_INVALID, promoCode);
        }

        try {
            cart = orderService.removeOfferCode(cart, offerCode, priceOrder);
            OrderWrapper wrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
            wrapper.wrapDetails(cart, request);

            return wrapper;
        } catch (PricingException e) {
            throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                    .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
        }

    }

    public OrderWrapper removeAllOfferCodes(HttpServletRequest request,
            boolean priceOrder) {

        Order cart = CartState.getCart();

        if (cart == null || cart instanceof NullOrderImpl) {
            throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                    .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
        }

        try {
            cart = orderService.removeAllOfferCodes(cart, priceOrder);
            return wrapCart(request, cart);

        } catch (PricingException e) {
            throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                    .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
        }
    }

    public OrderWrapper updateProductOptions(HttpServletRequest request,
            MultiValueMap<String, String> requestParams,
            Long itemId,
            boolean priceOrder) {

        Order cart = CartState.getCart();

        if (cart != null) {
            try {
                OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();

                HashMap<String, String> productOptions = getOptions(requestParams);
                orderItemRequestDTO.setOrderItemId(itemId);
                //If we have product options set them on the DTO
                if (productOptions.size() > 0) {
                    orderItemRequestDTO.setItemAttributes(productOptions);
                }
                Order order = orderService.updateProductOptionsForItem(cart.getId(), orderItemRequestDTO, priceOrder);

                order = orderService.save(order, priceOrder);
                return wrapCart(request, cart);
            } catch (UpdateCartException e) {
                if (e.getCause() instanceof ItemNotFoundException) {
                    throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value(), null, null, e.getCause())
                    .addMessage(BroadleafWebServicesException.CART_ITEM_NOT_FOUND, itemId);
                } else {
                    throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e)
                            .addMessage(BroadleafWebServicesException.UPDATE_CART_ERROR);
                }
            } catch (PricingException pe) {
                throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, pe)
                        .addMessage(BroadleafWebServicesException.CART_PRICING_ERROR);
            }
        }
        throw BroadleafWebServicesException.build(HttpStatus.NOT_FOUND.value())
                .addMessage(BroadleafWebServicesException.CART_NOT_FOUND);
    }

    protected OrderWrapper wrapCart(HttpServletRequest request, Order cart) {

        try {
            OrderWrapper orderWrapper = (OrderWrapper) context.getBean(OrderWrapper.class.getName());
            orderWrapper.wrapDetails(cart, request);
            return orderWrapper;
        } catch (BeansException e) {
            throw BroadleafWebServicesException.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null, e);
        }

    }
}
