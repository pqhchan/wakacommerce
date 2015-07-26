
package com.wakacommerce.profile.core.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.profile.core.domain.Country;
import com.wakacommerce.profile.core.domain.CountryImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("blCountryDao")
public class CountryDaoImpl implements CountryDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public Country findCountryByAbbreviation(String abbreviation) {
        return (Country) em.find(CountryImpl.class, abbreviation);
    }

    @SuppressWarnings("unchecked")
    public List<Country> findCountries() {
        Query query = em.createNamedQuery("BC_FIND_COUNTRIES");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }
    
    public Country save(Country country) {
        return em.merge(country);
    }
}
