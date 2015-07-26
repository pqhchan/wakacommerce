
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.Customer;

public interface CustomerDao {

    public Customer readCustomerById(Long id);

    /**
     * Returns the first customer that match the passed in username, with caching defaulted.
     * 
     * @param username
     * @return
     */
    public Customer readCustomerByUsername(String username);

    /**
     * Returns the first customer that match the passed in username, and caches according to
     * cacheable.
     * 
     * @param username
     * @param cacheable
     * @return
     */
    public Customer readCustomerByUsername(String username, Boolean cacheable);

    /**
     * Returns all customers that match the passed in username, with caching defaulted.
     * 
     * @param username
     * @return
     */
    public List<Customer> readCustomersByUsername(String username);

    /**
     * Returns all customers that match the passed in username, and caches according to
     * cacheable.
     * 
     * @param username
     * @param cacheable
     * @return
     */
    public List<Customer> readCustomersByUsername(String username, Boolean cacheable);

    public Customer save(Customer customer);

    /**
     * Returns the first customer that matches the passed in email.
     * 
     * @param emailAddress
     * @return
     */
    public Customer readCustomerByEmail(String emailAddress);
    
    /**
     * Returns all customers that matches the passed in email.
     * 
     * @param emailAddress
     * @return
     */
    public List<Customer> readCustomersByEmail(String emailAddress);

    public Customer create();

    /**
     * Remove a customer from the persistent store
     *
     * @param customer the customer entity to remove
     */
    void delete(Customer customer);
}
