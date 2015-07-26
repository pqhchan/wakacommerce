
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerRole;
import com.wakacommerce.profile.core.domain.Role;

public interface RoleDao {

    public List<CustomerRole> readCustomerRolesByCustomerId(Long customerId);
    
    public Role readRoleByName(String name);
    
    public void addRoleToCustomer(CustomerRole customerRole);
    
    public void removeCustomerRolesByCustomerId(Long customerId);
}
