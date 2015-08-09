
package com.wakacommerce.core.web.service;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.call.UpdateCartResponse;

/**
 *
 * @ hui
 */
public interface UpdateCartService {

    public void setSavedCurrency(BroadleafCurrency savedCurrency);

    public BroadleafCurrency getSavedCurrency();

    public boolean currencyHasChanged();

    public UpdateCartResponse copyCartToCurrentContext(Order currentCart);

    public void validateCart (Order cart) throws IllegalArgumentException;

    public void updateAndValidateCart(Order cart);

}
