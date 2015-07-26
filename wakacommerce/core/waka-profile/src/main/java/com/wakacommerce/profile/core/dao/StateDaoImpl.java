
package com.wakacommerce.profile.core.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.profile.core.domain.Country;
import com.wakacommerce.profile.core.domain.CountryImpl;
import com.wakacommerce.profile.core.domain.State;
import com.wakacommerce.profile.core.domain.StateImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @deprecated - use {@link com.wakacommerce.profile.core.dao.CountrySubdivisionDaoImpl} instead.
 */
@Deprecated
@Repository("blStateDao")
public class StateDaoImpl implements StateDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public State findStateByAbbreviation(String abbreviation) {
        return (State) em.find(StateImpl.class, abbreviation);
    }

    @SuppressWarnings("unchecked")
    public List<State> findStates() {
        Query query = em.createNamedQuery("BC_FIND_STATES");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<State> findStates(String countryAbbreviation) {
        Query query = em.createNamedQuery("BC_FIND_STATES_BY_COUNTRY_ABBREVIATION");
        query.setParameter("countryAbbreviation", countryAbbreviation);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    public Country findCountryByShortName(String shortName) {
        return (Country) em.find(CountryImpl.class, shortName);
    }

    @SuppressWarnings("unchecked")
    public List<Country> findCountries() {
        Query query = em.createNamedQuery("BC_FIND_COUNTRIES");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    public State create() {
        return (State) entityConfiguration.createEntityInstance(State.class.getName());
    }
    
    public State save(State state) {
        return em.merge(state);
    }
}
