

package com.wakacommerce.profile.core.domain;

import java.io.Serializable;
import java.util.Map;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface CustomerPayment extends Serializable, AdditionalFields, MultiTenantCloneable<CustomerPayment> {

    public void setId(Long id);

    public Long getId();

    public Customer getCustomer();

    public void setCustomer(Customer customer);

    public Address getBillingAddress();

    public void setBillingAddress(Address billingAddress);

    public String getPaymentToken();

    public void setPaymentToken(String paymentToken);

    public boolean isDefault();

    public void setDefault(boolean isDefault);

    public Map<String, String> getAdditionalFields();

    public void setAdditionalFields(Map<String, String> additionalFields);

}
