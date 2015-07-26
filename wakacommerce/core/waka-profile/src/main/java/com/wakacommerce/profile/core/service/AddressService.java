
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.service.exception.AddressVerificationException;

public interface AddressService {

    public Address saveAddress(Address address);

    public Address readAddressById(Long addressId);

    public Address create();

    public void delete(Address address);

    /**
     * Verifies the address and returns a collection of addresses. If the address was 
     * invalid but close to a match, this method should return a list of one or more addresses that may be valid. 
     * If the address is valid, implementations should return the valid address in the list. 
     * Implementations may set the tokenized address, zip four, and verification level. If the address could not 
     * be validated, implementors should throw an <code>AddressValidationException</code>.
     * 
     * For example, an address may be close, but missing zip four. This service should return 
     * the address in question with zip four populated.
     * @param address
     * @return
     */
    public List<Address> verifyAddress(Address address) throws AddressVerificationException;

}
