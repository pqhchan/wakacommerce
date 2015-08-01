  
package com.wakacommerce.profile.web.core.security;

import org.springframework.context.ApplicationEvent;

import com.wakacommerce.profile.core.domain.Customer;

public class CustomerLoggedInEvent extends ApplicationEvent {
    
    private static final long serialVersionUID = 1L;
    
    private Customer customer;

    public CustomerLoggedInEvent(Customer customer, Object source) {
        super(source);
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
