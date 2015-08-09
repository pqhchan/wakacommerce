
package com.wakacommerce.core.web.controller.cart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacommerce.common.util.BLCMessageUtils;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.exception.OfferAlreadyAddedException;
import com.wakacommerce.core.offer.service.exception.OfferException;
import com.wakacommerce.core.offer.service.exception.OfferExpiredException;
import com.wakacommerce.core.offer.service.exception.OfferMaxUseExceededException;
import com.wakacommerce.core.order.domain.NullOrderImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.call.AddToCartItem;
import com.wakacommerce.core.order.service.exception.AddToCartException;
import com.wakacommerce.core.order.service.exception.IllegalCartOperationException;
import com.wakacommerce.core.order.service.exception.ItemNotFoundException;
import com.wakacommerce.core.order.service.exception.RemoveFromCartException;
import com.wakacommerce.core.order.service.exception.UpdateCartException;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.profile.web.core.CustomerState;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
public class BroadleafCartController extends AbstractCartController {
    
    protected static String cartView = "cart/cart";
    protected static String cartPageRedirect = "redirect:/cart";
    
    @Value("${solr.index.use.sku}")
    protected boolean useSku;

    public String cart(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
        Order cart = CartState.getCart();
        if (cart != null && !(cart instanceof NullOrderImpl)) {
            model.addAttribute("paymentRequestDTO", dtoTranslationService.translateOrder(CartState.getCart()));
        }
        return getCartView();
    }

    public String add(HttpServletRequest request, HttpServletResponse response, Model model,
            AddToCartItem itemRequest) throws IOException, AddToCartException, PricingException  {
        Order cart = CartState.getCart();
        
        // If the cart is currently empty, it will be the shared, "null" cart. We must detect this
        // and provision a fresh cart for the current customer upon the first cart add
        if (cart == null || cart instanceof NullOrderImpl) {
            cart = orderService.createNewCartForCustomer(CustomerState.getCustomer(request));
        }

        updateCartService.validateCart(cart);

        cart = orderService.addItem(cart.getId(), itemRequest, false);
        cart = orderService.save(cart,  true);
        
        return isAjaxRequest(request) ? getCartView() : getCartPageRedirect();
    }

    public String addWithPriceOverride(HttpServletRequest request, HttpServletResponse response, Model model,
            AddToCartItem itemRequest) throws IOException, AddToCartException, PricingException {
        Order cart = CartState.getCart();

        // If the cart is currently empty, it will be the shared, "null" cart. We must detect this
        // and provision a fresh cart for the current customer upon the first cart add
        if (cart == null || cart instanceof NullOrderImpl) {
            cart = orderService.createNewCartForCustomer(CustomerState.getCustomer(request));
        }

        updateCartService.validateCart(cart);

        cart = orderService.addItemWithPriceOverrides(cart.getId(), itemRequest, false);
        cart = orderService.save(cart, true);

        return isAjaxRequest(request) ? getCartView() : getCartPageRedirect();
    }

    public String updateQuantity(HttpServletRequest request, HttpServletResponse response, Model model,
            AddToCartItem itemRequest) throws IOException, UpdateCartException, PricingException, RemoveFromCartException {
        Order cart = CartState.getCart();

        cart = orderService.updateItemQuantity(cart.getId(), itemRequest, true);
        cart = orderService.save(cart, false);
        
        if (isAjaxRequest(request)) {
            Map<String, Object> extraData = new HashMap<String, Object>();
            if(useSku) {
                extraData.put("skuId", itemRequest.getSkuId());
            } else {
                extraData.put("productId", itemRequest.getProductId());
            }
            extraData.put("cartItemCount", cart.getItemCount());
            model.addAttribute("blcextradata", new ObjectMapper().writeValueAsString(extraData));
            return getCartView();
        } else {
            return getCartPageRedirect();
        }
    }

    public String remove(HttpServletRequest request, HttpServletResponse response, Model model,
            AddToCartItem itemRequest) throws IOException, PricingException, RemoveFromCartException {
        Order cart = CartState.getCart();
        
        cart = orderService.removeItem(cart.getId(), itemRequest.getOrderItemId(), false);
        cart = orderService.save(cart, true);
        
        if (isAjaxRequest(request)) {
            Map<String, Object> extraData = new HashMap<String, Object>();
            extraData.put("cartItemCount", cart.getItemCount());
            if(useSku) {
                extraData.put("skuId", itemRequest.getSkuId());
            } else {
                extraData.put("productId", itemRequest.getProductId());
            }
            model.addAttribute("blcextradata", new ObjectMapper().writeValueAsString(extraData));
            return getCartView();
        } else {
            return getCartPageRedirect();
        }
    }

    public String empty(HttpServletRequest request, HttpServletResponse response, Model model) throws PricingException {
        Order cart = CartState.getCart();
        orderService.cancelOrder(cart);
        CartState.setCart(null);
        return "redirect:/";
    }
    
    /** Attempts to add provided Offer to Cart
     * 
     * @param request
     * @param response
     * @param model
     * @param customerOffer
     * @return the return view
     * @throws IOException
     * @throws PricingException
     * @throws ItemNotFoundException
     * @throws OfferMaxUseExceededException 
     */
    public String addPromo(HttpServletRequest request, HttpServletResponse response, Model model,
            String customerOffer) throws IOException, PricingException {
        Order cart = CartState.getCart();
        
        Boolean promoAdded = false;
        String exception = "";
        
        if (cart != null && !(cart instanceof NullOrderImpl)) {
            OfferCode offerCode = offerService.lookupOfferCodeByCode(customerOffer);
            if (offerCode != null) {
                try {
                    orderService.addOfferCode(cart, offerCode, false);
                    promoAdded = true;
                    cart = orderService.save(cart, true);
                } catch (OfferException e) {
                    if (e instanceof OfferMaxUseExceededException) {
                        exception = "Use Limit Exceeded";
                    } else if (e instanceof OfferExpiredException) {
                        exception = "Offer Has Expired";
                    } else if (e instanceof OfferAlreadyAddedException) {
                        exception = "Offer Has Already Been Added";
                    } else {
                        exception = "An Unknown Offer Error Has Occured";
                    }
                }
            } else {
                exception = "Invalid Code";
            }
        } else {
            exception = "Invalid Cart";
        }

        if (isAjaxRequest(request)) {
            Map<String, Object> extraData = new HashMap<String, Object>();
            extraData.put("promoAdded", promoAdded);
            extraData.put("exception" , exception);
            model.addAttribute("blcextradata", new ObjectMapper().writeValueAsString(extraData));
            return getCartView();
        } else {
            model.addAttribute("exception", exception);
            return getCartView();
        }
        
    }
    
    /** Removes offer from cart
     * 
     * @param request
     * @param response
     * @param model
     * @return the return view
     * @throws IOException
     * @throws PricingException
     * @throws ItemNotFoundException
     * @throws OfferMaxUseExceededException 
     */
    public String removePromo(HttpServletRequest request, HttpServletResponse response, Model model,
            Long offerCodeId) throws IOException, PricingException {
        Order cart = CartState.getCart();
        
        OfferCode offerCode = offerService.findOfferCodeById(offerCodeId);

        orderService.removeOfferCode(cart, offerCode, false);
        cart = orderService.save(cart, true);

        return isAjaxRequest(request) ? getCartView() : getCartPageRedirect();
    }

    public String getCartView() {
        return cartView;
    }

    public String getCartPageRedirect() {
        return cartPageRedirect;
    }
    
    public Map<String, String> handleIllegalCartOpException(IllegalCartOperationException ex) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put("error", "illegalCartOperation");
        returnMap.put("exception", BLCMessageUtils.getMessage(ex.getType()));
        return returnMap;
    }
    

}
