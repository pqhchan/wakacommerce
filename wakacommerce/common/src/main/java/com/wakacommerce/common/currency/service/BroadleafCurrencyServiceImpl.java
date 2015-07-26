
package com.wakacommerce.common.currency.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.currency.dao.BroadleafCurrencyDao;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: jerryocanas
 * Date: 9/6/12
 */

@Service("blCurrencyService")
public class BroadleafCurrencyServiceImpl implements BroadleafCurrencyService {

    @Resource(name="blCurrencyDao")
    protected BroadleafCurrencyDao currencyDao;

    /**
     * Returns the default Broadleaf currency
     * @return The default currency
     */
    @Override
    public BroadleafCurrency findDefaultBroadleafCurrency() {
        return currencyDao.findDefaultBroadleafCurrency();
    }

    /**
     * @return The currency for the passed in code
     */
    @Override
    public BroadleafCurrency findCurrencyByCode(String currencyCode) {
        return currencyDao.findCurrencyByCode(currencyCode);
    }

    /**
     * Returns a list of all the Broadleaf Currencies
     *@return List of currencies
     */
    @Override
    public List<BroadleafCurrency> getAllCurrencies() {
        return currencyDao.getAllCurrencies();
    }

    @Override
    public BroadleafCurrency save(BroadleafCurrency currency) {
        return currencyDao.save(currency);
    }
    
    @Override
    public BroadleafCurrency create() {
        return currencyDao.create();
    }    
    
}
