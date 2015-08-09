
package com.wakacommerce.profile.core.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.profile.core.domain.CountrySubdivision;
import com.wakacommerce.profile.core.domain.CountrySubdivisionImpl;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @ hui
 */
@Repository("blCountrySubdivisionDao")
public class CountrySubdivisionDaoImpl implements CountrySubdivisionDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public CountrySubdivision findSubdivisionByAbbreviation(String abbreviation) {
        return (CountrySubdivision) em.find(CountrySubdivisionImpl.class, abbreviation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CountrySubdivision> findSubdivisions() {
        Query query = em.createNamedQuery("BC_FIND_COUNTRY_SUBDIVISIONS");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CountrySubdivision> findSubdivisions(String countryAbbreviation) {
        Query query = em.createNamedQuery("BC_FIND_SUBDIVISIONS_BY_COUNTRY_ABBREVIATION");
        query.setParameter("countryAbbreviation", countryAbbreviation);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public List<CountrySubdivision> findSubdivisionsByCountryAndCategory(String countryAbbreviation, String category) {
        Query query = em.createNamedQuery("BC_FIND_SUBDIVISIONS_BY_COUNTRY_ABBREVIATION_AND_CATEGORY");
        query.setParameter("countryAbbreviation", countryAbbreviation);
        query.setParameter("category", category);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public CountrySubdivision create() {
        return (CountrySubdivision) entityConfiguration.createEntityInstance(CountrySubdivision.class.getName());
    }

    @Override
    public CountrySubdivision save(CountrySubdivision state) {
        return em.merge(state);
    }
}

