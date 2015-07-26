
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerRole;

public interface RoleService {

    public List<CustomerRole> findCustomerRolesByCustomerId(Long customerId);
}
