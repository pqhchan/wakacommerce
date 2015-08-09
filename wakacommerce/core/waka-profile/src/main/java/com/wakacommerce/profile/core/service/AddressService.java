
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.service.exception.AddressVerificationException;

public interface AddressService {

    public Address saveAddress(Address address);

    public Address readAddressById(Long addressId);

    public Address create();

    public void delete(Address address);

    public List<Address> verifyAddress(Address address) throws AddressVerificationException;

}
