
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.CustomerPayment;

public interface CustomerPaymentDao {

    public List<CustomerPayment> readCustomerPaymentsByCustomerId(Long customerId);

    public CustomerPayment save(CustomerPayment customerPayment);

    public CustomerPayment readCustomerPaymentById(Long customerPaymentId);

    public CustomerPayment readCustomerPaymentByToken(String token);

    public void deleteCustomerPaymentById(Long customerPaymentId);

    public CustomerPayment create();

}
