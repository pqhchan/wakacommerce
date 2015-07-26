
package com.wakacommerce.profile.core.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.AddressImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("blAddressDao")
public class AddressDaoImpl implements AddressDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public Address save(Address address) {
        return em.merge(address);
    }

    public Address readAddressById(Long id) {
        return (Address) em.find(AddressImpl.class, id);
    }

    public Address create() {
        return (Address) entityConfiguration.createEntityInstance(Address.class.getName());
    }

    public void delete(Address address) {
        if (!em.contains(address)) {
            address = readAddressById(address.getId());
        }
        em.remove(address);
    }
}
