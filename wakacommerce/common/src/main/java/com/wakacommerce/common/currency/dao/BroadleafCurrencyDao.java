
package com.wakacommerce.common.currency.dao;

import java.util.List;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;

/**
 *
 * @ hui
 */
public interface BroadleafCurrencyDao {

    public BroadleafCurrency findDefaultBroadleafCurrency();

    public BroadleafCurrency findCurrencyByCode(String currencyCode);

    public List<BroadleafCurrency> getAllCurrencies();

    public BroadleafCurrency save(BroadleafCurrency currency);
    
    public BroadleafCurrency create();

}
