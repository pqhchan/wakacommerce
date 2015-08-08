package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerPhone;

public interface CustomerPhoneService {

    public CustomerPhone saveCustomerPhone(CustomerPhone customerPhone);

    public List<CustomerPhone> readActiveCustomerPhonesByCustomerId(Long customerId);

    public CustomerPhone readCustomerPhoneById(Long customerPhoneId);

    public void makeCustomerPhoneDefault(Long customerPhoneId, Long customerId);

    public void deleteCustomerPhoneById(Long customerPhoneId);

    public CustomerPhone findDefaultCustomerPhone(Long customerId);

    public List<CustomerPhone> readAllCustomerPhonesByCustomerId(Long customerId);

    public CustomerPhone create();

}
