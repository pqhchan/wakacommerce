
package com.wakacommerce.common.money;

import java.util.Currency;

public interface CurrencyConversionService {

    public Money convertCurrency(Money source, Currency destinationCurrency, int destinationScale);
    
}
