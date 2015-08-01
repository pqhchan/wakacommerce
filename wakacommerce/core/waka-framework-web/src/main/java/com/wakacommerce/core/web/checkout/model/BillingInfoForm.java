
package com.wakacommerce.core.web.checkout.model;

import java.io.Serializable;

import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.AddressImpl;
import com.wakacommerce.profile.core.domain.PhoneImpl;

/**
 * <p>A form to model adding the Billing Address to the Order</p>
 *
 *  
 *   ( )
 */
public class BillingInfoForm implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Address address = new AddressImpl();
    protected boolean useShippingAddress;

    public BillingInfoForm() {
        address.setPhonePrimary(new PhoneImpl());
        address.setPhoneSecondary(new PhoneImpl());
        address.setPhoneFax(new PhoneImpl());
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isUseShippingAddress() {
        return useShippingAddress;
    }

    public void setUseShippingAddress(boolean useShippingAddress) {
        this.useShippingAddress = useShippingAddress;
    }
}
