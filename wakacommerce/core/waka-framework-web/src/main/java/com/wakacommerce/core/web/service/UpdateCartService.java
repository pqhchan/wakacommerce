
package com.wakacommerce.core.web.service;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.call.UpdateCartResponse;

/**
 * Provides methods to facilitate order repricing.
 *
 * Author: jerryocanas
 * Date: 9/26/12
 */
public interface UpdateCartService {

    /**
     * Sets the currency that was set as active on last pass through.
     *
     * @param savedCurrency
     */
    public void setSavedCurrency(BroadleafCurrency savedCurrency);

    /**
     * Gets the currency that was set as active on last pass through.
     *
     * @return
     */
    public BroadleafCurrency getSavedCurrency();

    /**
     *  Compares the currency set in the WakaRequestContext with the savedCurrency.
     *  If different, returns TRUE
     *
     * @return
     */
    public boolean currencyHasChanged();

    /**
     * Reprices the order by removing all items and recreating the cart calling for a reprice on the new cart.
     *
     * @return
     */
    public UpdateCartResponse copyCartToCurrentContext(Order currentCart);

    /**
     * Validates the cart against the active price list and locale.
     *
     * @param cart
     * @throws IllegalArgumentException
     */
    public void validateCart (Order cart) throws IllegalArgumentException;

    /**
     * Updates the cart (locale, pricing) and performs validation.
     *
     * @param cart
     * @throws IllegalArgumentException
     */
    public void updateAndValidateCart(Order cart);

}
