
package com.wakacommerce.common.currency.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.persistence.EntityConfiguration;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @ hui
 */

@Repository("blCurrencyDao")
public class BroadleafCurrencyDaoImpl implements BroadleafCurrencyDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public BroadleafCurrency findDefaultBroadleafCurrency() {
        Query query = em.createNamedQuery("BC_READ_DEFAULT_CURRENCY");
        query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);
        List<BroadleafCurrency> currencyList = query.getResultList();
        if (currencyList.size() >= 1) {
            return currencyList.get(0);
        }
        return null;
    }

    @Override
    public BroadleafCurrency findCurrencyByCode(String currencyCode) {
        Query query = em.createNamedQuery("BC_READ_CURRENCY_BY_CODE");
        query.setParameter("currencyCode", currencyCode);
        query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);
        List<BroadleafCurrency> currencyList = query.getResultList();
        if (currencyList.size() >= 1) {
            return currencyList.get(0);
        }
        return null;
    }

    @Override
    public List<BroadleafCurrency> getAllCurrencies() {
        Query query = em.createNamedQuery("BC_READ_ALL_CURRENCIES");
        query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public BroadleafCurrency save(BroadleafCurrency currency) {
        return em.merge(currency);
    }
    
    @Override
    public BroadleafCurrency create() {
        return entityConfiguration.createEntityInstance(BroadleafCurrency.class.getName(), BroadleafCurrency.class);
    }    
}
