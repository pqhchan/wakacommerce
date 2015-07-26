
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.domain.CustomerPayment;

public interface CustomerPaymentService {

    public CustomerPayment saveCustomerPayment(CustomerPayment customerPayment);

    public List<CustomerPayment> readCustomerPaymentsByCustomerId(Long customerId);

    public CustomerPayment readCustomerPaymentById(Long customerPaymentId);

    public CustomerPayment readCustomerPaymentByToken(String token);

    public void deleteCustomerPaymentById(Long customerPaymentId);

    public CustomerPayment create();

    public CustomerPayment findDefaultPaymentForCustomer(Customer customer);

    public CustomerPayment setAsDefaultPayment(CustomerPayment payment);

    public Customer deleteCustomerPaymentFromCustomer(Customer customer, CustomerPayment payment);

}
