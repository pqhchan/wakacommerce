package com.wakacommerce.profile.core.domain;

import org.springframework.context.ApplicationEvent;


/**
 *
 * @ hui
 */
public class CustomerPersistedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public CustomerPersistedEvent(Customer customer) {
        super(customer);
    }

    public Customer getCustomer() {
        return (Customer)source;
    }

}
