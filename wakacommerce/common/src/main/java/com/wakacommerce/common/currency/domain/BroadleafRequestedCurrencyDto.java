
package com.wakacommerce.common.currency.domain;

/**
 *
 * @ hui
 */
public class BroadleafRequestedCurrencyDto {

    BroadleafCurrency currencyToUse;
    BroadleafCurrency requestedCurrency;

    public BroadleafRequestedCurrencyDto(BroadleafCurrency currencyToUse, BroadleafCurrency requestedCurrency) {
        super();
        this.currencyToUse = currencyToUse;
        this.requestedCurrency = requestedCurrency;
    }

    public BroadleafCurrency getCurrencyToUse() {
        return currencyToUse;
    }

    public BroadleafCurrency getRequestedCurrency() {
        return requestedCurrency;
    }

}
