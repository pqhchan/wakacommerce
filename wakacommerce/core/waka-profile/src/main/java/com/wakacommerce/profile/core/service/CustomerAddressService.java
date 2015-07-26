
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerAddress;

public interface CustomerAddressService {

    public CustomerAddress saveCustomerAddress(CustomerAddress customerAddress);

    public List<CustomerAddress> readActiveCustomerAddressesByCustomerId(Long customerId);

    public CustomerAddress readCustomerAddressById(Long customerAddressId);

    public void makeCustomerAddressDefault(Long customerAddressId, Long customerId);

    public void deleteCustomerAddressById(Long customerAddressId);

    public CustomerAddress findDefaultCustomerAddress(Long customerId);

    public CustomerAddress create();

}
