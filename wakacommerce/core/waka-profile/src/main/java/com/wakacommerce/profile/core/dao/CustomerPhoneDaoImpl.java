package com.wakacommerce.profile.core.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.profile.core.domain.CustomerPhone;
import com.wakacommerce.profile.core.domain.CustomerPhoneImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("blCustomerPhoneDao")
public class CustomerPhoneDaoImpl implements CustomerPhoneDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @SuppressWarnings("unchecked")
    public List<CustomerPhone> readActiveCustomerPhonesByCustomerId(Long customerId) {
        Query query = em.createNamedQuery("BC_READ_ACTIVE_CUSTOMER_PHONES_BY_CUSTOMER_ID");
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    public CustomerPhone save(CustomerPhone customerPhone) {
        return em.merge(customerPhone);
    }

    public CustomerPhone readCustomerPhoneById(Long customerPhoneId) {
        return (CustomerPhone) em.find(CustomerPhoneImpl.class, customerPhoneId);
    }

    public void makeCustomerPhoneDefault(Long customerPhoneId, Long customerId) {
        List<CustomerPhone> customerPhones = readActiveCustomerPhonesByCustomerId(customerId);
        for (CustomerPhone customerPhone : customerPhones) {
            customerPhone.getPhone().setDefault(customerPhone.getId().equals(customerPhoneId));
            em.merge(customerPhone);
        }
    }

    public void deleteCustomerPhoneById(Long customerPhoneId) {
        CustomerPhone customerPhone = readCustomerPhoneById(customerPhoneId);
        if (customerPhone != null) {
            em.remove(customerPhone);
        }
    }

    @SuppressWarnings("unchecked")
    public CustomerPhone findDefaultCustomerPhone(Long customerId) {
        Query query = em.createNamedQuery("BC_FIND_DEFAULT_PHONE_BY_CUSTOMER_ID");
        query.setParameter("customerId", customerId);
        List<CustomerPhone> customerPhones = query.getResultList();
        return customerPhones.isEmpty() ? null : customerPhones.get(0);
    }

    @SuppressWarnings("unchecked")
    public List<CustomerPhone> readAllCustomerPhonesByCustomerId(Long customerId) {
        Query query = em.createNamedQuery("BC_READ_ALL_CUSTOMER_PHONES_BY_CUSTOMER_ID");
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    public CustomerPhone create() {
        return (CustomerPhone) entityConfiguration.createEntityInstance(CustomerPhone.class.getName());
    }
}
