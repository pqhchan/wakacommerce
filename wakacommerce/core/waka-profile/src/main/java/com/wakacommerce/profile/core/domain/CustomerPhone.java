
package com.wakacommerce.profile.core.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface CustomerPhone extends Serializable, MultiTenantCloneable<CustomerPhone> {

    public void setId(Long id);

    public Long getId();

    public void setPhoneName(String phoneName);

    public String getPhoneName();

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public Phone getPhone();

    public void setPhone(Phone phone);

}
