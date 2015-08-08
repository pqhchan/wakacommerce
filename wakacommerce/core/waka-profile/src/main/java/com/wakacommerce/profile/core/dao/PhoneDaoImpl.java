package com.wakacommerce.profile.core.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.profile.core.domain.Phone;
import com.wakacommerce.profile.core.domain.PhoneImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("blPhoneDao")
public class PhoneDaoImpl implements PhoneDao {

    @PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public Phone save(Phone phone) {
        return em.merge(phone);
    }

    public Phone readPhoneById(Long phoneId) {
        return (Phone) em.find(PhoneImpl.class, phoneId);
    }

    public Phone create() {
        return (Phone) entityConfiguration.createEntityInstance(Phone.class.getName());
    }
}
