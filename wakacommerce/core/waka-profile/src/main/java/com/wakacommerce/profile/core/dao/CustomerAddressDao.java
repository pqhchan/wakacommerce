
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerAddress;

public interface CustomerAddressDao {

    public List<CustomerAddress> readActiveCustomerAddressesByCustomerId(Long customerId);

    public CustomerAddress save(CustomerAddress customerAddress);

    public CustomerAddress readCustomerAddressById(Long customerAddressId);

    public void makeCustomerAddressDefault(Long customerAddressId, Long customerId);

    public void deleteCustomerAddressById(Long customerAddressId);

    public CustomerAddress findDefaultCustomerAddress(Long customerId);

    public CustomerAddress create();

}
