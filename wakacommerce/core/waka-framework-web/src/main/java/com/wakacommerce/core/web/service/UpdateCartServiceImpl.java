
package com.wakacommerce.core.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.call.AddToCartItem;
import com.wakacommerce.core.order.service.call.UpdateCartResponse;
import com.wakacommerce.core.order.service.exception.AddToCartException;
import com.wakacommerce.core.order.service.exception.RemoveFromCartException;
import com.wakacommerce.core.pricing.service.exception.PricingException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blUpdateCartService")
public class UpdateCartServiceImpl implements UpdateCartService {
    protected static final Log LOG = LogFactory.getLog(UpdateCartServiceImpl.class);

    protected static BroadleafCurrency savedCurrency;

    @Resource(name="blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blUpdateCartServiceExtensionManager")
    protected UpdateCartServiceExtensionManager extensionManager;

    @Override
    public boolean currencyHasChanged() {
        BroadleafCurrency currency = findActiveCurrency();
        if (getSavedCurrency() == null) {
            setSavedCurrency(currency);
        } else if (getSavedCurrency() != currency){
            return true;
        }
        return false;
    }

    @Override
    public UpdateCartResponse copyCartToCurrentContext(Order currentCart) {
        if(currentCart.getOrderItems() == null){
            return null;
        }
        BroadleafCurrency currency = findActiveCurrency();
        if(currency == null){
            return null;
        }

        //Reprice order logic
        List<AddToCartItem> itemsToReprice = new ArrayList<AddToCartItem>();
        List<OrderItem> itemsToRemove = new ArrayList<OrderItem>();
        List<OrderItem> itemsToReset = new ArrayList<OrderItem>();
        boolean repriceOrder = true;

        for(OrderItem orderItem: currentCart.getOrderItems()){
            //Lookup price in price list, if null, then add to itemsToRemove
            if (orderItem instanceof DiscreteOrderItem){
                DiscreteOrderItem doi = (DiscreteOrderItem) orderItem;
                if(checkAvailabilityInLocale(doi, currency)){
                    AddToCartItem itemRequest = new AddToCartItem();
                    itemRequest.setProductId(doi.getProduct().getId());
                    itemRequest.setQuantity(doi.getQuantity());
                    itemsToReprice.add(itemRequest);
                    itemsToReset.add(orderItem);
                } else {
                    itemsToRemove.add(orderItem);
                }
            } else if (orderItem instanceof BundleOrderItem) {
                BundleOrderItem boi = (BundleOrderItem) orderItem;
                for (DiscreteOrderItem doi : boi.getDiscreteOrderItems()) {
                    if(checkAvailabilityInLocale(doi, currency)){
                        AddToCartItem itemRequest = new AddToCartItem();
                        itemRequest.setProductId(doi.getProduct().getId());
                        itemRequest.setQuantity(doi.getQuantity());
                        itemsToReprice.add(itemRequest);
                        itemsToReset.add(orderItem);
                    } else {
                        itemsToRemove.add(orderItem);
                    }
                }
            }
        }

        for(OrderItem orderItem: itemsToReset){
            try {
                currentCart = orderService.removeItem(currentCart.getId(), orderItem.getId(), false);
            } catch (RemoveFromCartException e) {
                e.printStackTrace();
            }
        }

        for(AddToCartItem itemRequest: itemsToReprice){
            try {
                currentCart = orderService.addItem(currentCart.getId(), itemRequest, false);
            } catch (AddToCartException e) {
                e.printStackTrace();
            }
        }

        // Reprice and save the cart
        try {
         currentCart = orderService.save(currentCart, repriceOrder);
        } catch (PricingException e) {
         e.printStackTrace();
        }
        setSavedCurrency(currency);

        UpdateCartResponse updateCartResponse = new UpdateCartResponse();
        updateCartResponse.setRemovedItems(itemsToRemove);
        updateCartResponse.setOrder(currentCart);

        return updateCartResponse;
    }

    @Override
    public void validateCart(Order cart) {
        // hook to allow override
    }

    @Override
    public void updateAndValidateCart(Order cart) {
        if (extensionManager != null) {
            ExtensionResultHolder erh = new ExtensionResultHolder();
            extensionManager.getProxy().updateAndValidateCart(cart, erh);
            Boolean clearCart = (Boolean) erh.getContextMap().get("clearCart");
            Boolean repriceCart = (Boolean) erh.getContextMap().get("repriceCart");
            Boolean saveCart = (Boolean) erh.getContextMap().get("saveCart");
            if (clearCart != null && clearCart.booleanValue()) {
                orderService.cancelOrder(cart);
                cart = orderService.createNewCartForCustomer(cart.getCustomer());
            } else {
                try {
                    if (repriceCart != null && repriceCart.booleanValue()) {
                        orderService.save(cart, true, true);
                    } else if (saveCart != null && saveCart.booleanValue()) {
                        orderService.save(cart, false);
                    }
                } catch (PricingException pe) {
                    LOG.error("Pricing Exception while validating cart.   Clearing cart.", pe);
                    orderService.cancelOrder(cart);
                    cart = orderService.createNewCartForCustomer(cart.getCustomer());
                }
            }
        }
    }

    protected BroadleafCurrency findActiveCurrency(){
        if(WakaRequestContext.hasLocale()){
            return WakaRequestContext.getWakaRequestContext().getBroadleafCurrency();
        }
        return null;
    }

    protected boolean checkAvailabilityInLocale(DiscreteOrderItem doi, BroadleafCurrency currency) {
        if (doi.getSku() != null && extensionManager != null) {
            Sku sku = doi.getSku();
            return sku.isAvailable();
        }
        
        return false;
    }

    @Override
    public void setSavedCurrency(BroadleafCurrency savedCurrency) {
        this.savedCurrency = savedCurrency;
    }

    @Override
    public BroadleafCurrency getSavedCurrency() {
        return savedCurrency;
    }


}
