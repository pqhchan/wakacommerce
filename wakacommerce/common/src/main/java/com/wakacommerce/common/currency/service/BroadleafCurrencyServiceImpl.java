
package com.wakacommerce.common.currency.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.currency.dao.BroadleafCurrencyDao;
import com.wakacommerce.common.currency.domain.BroadleafCurrency;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @ hui
 */

@Service("blCurrencyService")
public class BroadleafCurrencyServiceImpl implements BroadleafCurrencyService {

    @Resource(name="blCurrencyDao")
    protected BroadleafCurrencyDao currencyDao;

    @Override
    public BroadleafCurrency findDefaultBroadleafCurrency() {
        return currencyDao.findDefaultBroadleafCurrency();
    }

    @Override
    public BroadleafCurrency findCurrencyByCode(String currencyCode) {
        return currencyDao.findCurrencyByCode(currencyCode);
    }

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
