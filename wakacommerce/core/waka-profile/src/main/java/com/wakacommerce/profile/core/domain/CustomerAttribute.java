
package com.wakacommerce.profile.core.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.ValueAssignable;


/**
 * Implementations of this interface are used to hold data about a Customers Attributes.
 * <br>
 * For high volume sites, you should consider extending the BLC Customer entity instead of
 * relying on custom attributes as the extension mechanism is more performant under load.
 *
 * @see {@link CustomerAttributeImpl}, {@link Customer}
 * 
 *
 */
public interface CustomerAttribute extends ValueAssignable<String>, MultiTenantCloneable<CustomerAttribute> {

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId();

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id);

    /**
     * Gets the associated customer.
     *
     * @return the customer
     */
    public Customer getCustomer();

    /**
     * Sets the associated customer.
     *
     * @param customer
     */
    public void setCustomer(Customer customer);
}
