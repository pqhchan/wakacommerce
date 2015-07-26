
package com.wakacommerce.core.web.controller.account;

import java.io.Serializable;

import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.AddressImpl;
import com.wakacommerce.profile.core.domain.PhoneImpl;

public class CustomerAddressForm implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Address address = new AddressImpl();
    protected String addressName;
    protected Long customerAddressId;

    public CustomerAddressForm() {
        address.setPhonePrimary(new PhoneImpl());
        address.setPhoneSecondary(new PhoneImpl());
        address.setPhoneFax(new PhoneImpl());
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address.getPhonePrimary() == null) {
            address.setPhonePrimary(new PhoneImpl());
        }
        if (address.getPhoneSecondary() == null) {
            address.setPhoneSecondary(new PhoneImpl());
        }
        if (address.getPhoneFax() == null) {
            address.setPhoneFax(new PhoneImpl());
        }
        this.address = address;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Long getCustomerAddressId() {
        return customerAddressId;
    }

    public void setCustomerAddressId(Long customerAddressId) {
        this.customerAddressId = customerAddressId;
    }

}
