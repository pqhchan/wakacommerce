
package com.wakacommerce.core.web.checkout.model;

import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.domain.PersonalMessage;
import com.wakacommerce.core.order.domain.PersonalMessageImpl;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.AddressImpl;
import com.wakacommerce.profile.core.domain.PhoneImpl;

import java.io.Serializable;

/**
 * A form to model adding a shipping address with shipping options.
 * 
 *Elbert Bautista (ebautista)
 * 
 */
public class ShippingInfoForm implements Serializable {

    private static final long serialVersionUID = 1L;

	protected Address address = new AddressImpl();
    protected String addressName;
    protected FulfillmentOption fulfillmentOption;
    protected Long fulfillmentOptionId;
    protected PersonalMessage personalMessage = new PersonalMessageImpl();
    protected String deliveryMessage;
    protected boolean useBillingAddress;

    public ShippingInfoForm() {
        address.setPhonePrimary(new PhoneImpl());
        address.setPhoneSecondary(new PhoneImpl());
        address.setPhoneFax(new PhoneImpl());
    }

    public Long getFulfillmentOptionId() {
        return fulfillmentOptionId;
    }
    
    public void setFulfillmentOptionId(Long fulfillmentOptionId) {
        this.fulfillmentOptionId = fulfillmentOptionId;
    }
    
    public FulfillmentOption getFulfillmentOption() {
        return fulfillmentOption;
    }

    public void setFulfillmentOption(FulfillmentOption fulfillmentOption) {
        this.fulfillmentOption = fulfillmentOption;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    } 
    
    public String getDeliveryMessage() {
        return deliveryMessage;
    }
    
    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }
    
    public void setPersonalMessage(PersonalMessage personalMessage) {
        this.personalMessage = personalMessage;
    }
    
    public PersonalMessage getPersonalMessage() {
        return personalMessage;
    }

    public boolean isUseBillingAddress() {
        return useBillingAddress;
    }

    public void setUseBillingAddress(boolean useBillingAddress) {
        this.useBillingAddress = useBillingAddress;
    }
}
