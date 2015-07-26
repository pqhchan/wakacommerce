
package com.wakacommerce.core.offer.domain;

import java.io.Serializable;

import com.wakacommerce.profile.core.domain.Customer;

public interface CustomerOffer extends Serializable {

    public Long getId() ;

    public void setId(Long id) ;

    public Customer getCustomer() ;

    public void setCustomer(Customer customer) ;

    public Offer getOffer() ;

    public void setOffer(Offer offer) ;

}
