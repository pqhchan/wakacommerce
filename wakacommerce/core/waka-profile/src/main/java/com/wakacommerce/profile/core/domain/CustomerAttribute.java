package com.wakacommerce.profile.core.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.ValueAssignable;

/**
 *
 * @ hui
 */
public interface CustomerAttribute extends ValueAssignable<String>, MultiTenantCloneable<CustomerAttribute> {

    public Long getId();

    public void setId(Long id);

    public Customer getCustomer();

    public void setCustomer(Customer customer);
}
