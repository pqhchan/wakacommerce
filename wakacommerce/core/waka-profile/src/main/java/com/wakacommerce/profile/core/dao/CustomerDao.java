package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.Customer;

public interface CustomerDao {

    public Customer readCustomerById(Long id);

    public Customer readCustomerByUsername(String username);

    public Customer readCustomerByUsername(String username, Boolean cacheable);

    public List<Customer> readCustomersByUsername(String username);

    public List<Customer> readCustomersByUsername(String username, Boolean cacheable);

    public Customer save(Customer customer);

    public Customer readCustomerByEmail(String emailAddress);
    
    public List<Customer> readCustomersByEmail(String emailAddress);

    public Customer create();

    void delete(Customer customer);
}
