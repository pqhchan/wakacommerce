
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerPhone;

public interface CustomerPhoneDao {

    public List<CustomerPhone> readActiveCustomerPhonesByCustomerId(Long customerId);

    public CustomerPhone save(CustomerPhone customerPhone);

    public CustomerPhone readCustomerPhoneById(Long customerPhoneId);

    public void makeCustomerPhoneDefault(Long customerPhoneId, Long customerId);

    public void deleteCustomerPhoneById(Long customerPhoneId);

    public CustomerPhone findDefaultCustomerPhone(Long customerId);

    public List<CustomerPhone> readAllCustomerPhonesByCustomerId(Long customerId);

    public CustomerPhone create();

}
