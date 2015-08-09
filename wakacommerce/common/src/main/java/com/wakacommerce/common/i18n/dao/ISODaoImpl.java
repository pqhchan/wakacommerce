
package com.wakacommerce.common.i18n.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.i18n.domain.ISOCountry;
import com.wakacommerce.common.i18n.domain.ISOCountryImpl;
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
@Repository("blISODao")
public class ISODaoImpl implements ISODao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public ISOCountry findISOCountryByAlpha2Code(String alpha2) {
        return (ISOCountry) em.find(ISOCountryImpl.class, alpha2);
    }

    @SuppressWarnings("unchecked")
    public List<ISOCountry> findISOCountries() {
        Query query = em.createNamedQuery("BC_FIND_ISO_COUNTRIES");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    public ISOCountry save(ISOCountry isoCountry) {
        return em.merge(isoCountry);
    }
}
