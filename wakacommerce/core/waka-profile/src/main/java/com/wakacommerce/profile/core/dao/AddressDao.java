
package com.wakacommerce.profile.core.dao;

import com.wakacommerce.profile.core.domain.Address;

public interface AddressDao {

    public Address save(Address address);

    public Address readAddressById(Long addressId);

    public Address create();

    public void delete(Address address);

}
