
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.persistence.Status;

public interface CustomerAddress extends Status, Serializable, MultiTenantCloneable<CustomerAddress> {

    public void setId(Long id);

    public Long getId();

    public void setAddressName(String addressName);

    public String getAddressName();

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public Address getAddress();

    public void setAddress(Address address);
    
   
    

}
