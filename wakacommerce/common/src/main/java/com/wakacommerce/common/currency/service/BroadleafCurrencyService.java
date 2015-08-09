
package com.wakacommerce.common.currency.service;

import java.util.List;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;

/**
 *
 * @ hui
 */
public interface BroadleafCurrencyService {

    public BroadleafCurrency findDefaultBroadleafCurrency();

    public BroadleafCurrency findCurrencyByCode(String currencyCode);

    public List<BroadleafCurrency> getAllCurrencies();

    public BroadleafCurrency save(BroadleafCurrency currency);
    
    public BroadleafCurrency create();
}
