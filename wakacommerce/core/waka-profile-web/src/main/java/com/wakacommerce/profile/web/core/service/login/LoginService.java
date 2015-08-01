package com.wakacommerce.profile.web.core.service.login;

import org.springframework.security.core.Authentication;

import com.wakacommerce.profile.core.domain.Customer;

public interface LoginService {
    public Authentication loginCustomer(Customer customer);

    public Authentication loginCustomer(String username, String clearTextPassword);

    public void logoutCustomer();
}
