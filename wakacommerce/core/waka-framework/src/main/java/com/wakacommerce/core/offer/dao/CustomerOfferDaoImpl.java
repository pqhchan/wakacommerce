
package com.wakacommerce.core.offer.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.offer.domain.CustomerOffer;
import com.wakacommerce.core.offer.domain.CustomerOfferImpl;
import com.wakacommerce.profile.core.domain.Customer;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("blCustomerOfferDao")
public class CustomerOfferDaoImpl implements CustomerOfferDao {

    @PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public CustomerOffer create() {
        return ((CustomerOffer) entityConfiguration.createEntityInstance(CustomerOffer.class.getName()));
    }

    public void delete(CustomerOffer customerOffer) {
        if (!em.contains(customerOffer)) {
            customerOffer = readCustomerOfferById(customerOffer.getId());
        }
        em.remove(customerOffer);
    }

    public CustomerOffer save(final CustomerOffer customerOffer) {
        return em.merge(customerOffer);
    }

    public CustomerOffer readCustomerOfferById(final Long customerOfferId) {
        return (CustomerOffer) em.find(CustomerOfferImpl.class, customerOfferId);
    }

    @SuppressWarnings("unchecked")
    public List<CustomerOffer> readCustomerOffersByCustomer(final Customer customer) {
        final Query query = em.createNamedQuery("BC_READ_CUSTOMER_OFFER_BY_CUSTOMER_ID");
        query.setParameter("customerId", customer.getId());
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        query.setHint(QueryHints.HINT_CACHE_REGION, "query.Offer");

        return query.getResultList();
    }

}
