
package com.wakacommerce.core.offer.dao;

import com.wakacommerce.core.offer.domain.CustomerOffer;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;

public interface CustomerOfferDao {

    public CustomerOffer readCustomerOfferById(Long customerOfferId);

    public List<CustomerOffer> readCustomerOffersByCustomer(Customer customer);

    public CustomerOffer save(CustomerOffer customerOffer);

    public void delete(CustomerOffer customerOffer);

    public CustomerOffer create();
}
