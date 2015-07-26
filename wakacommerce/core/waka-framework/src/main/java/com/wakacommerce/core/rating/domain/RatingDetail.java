
package com.wakacommerce.core.rating.domain;

import java.util.Date;

import com.wakacommerce.profile.core.domain.Customer;

public interface RatingDetail {

    public Long getId();
    
    public void setId(Long id);
    
    public Double getRating();
    
    public void setRating(Double newRating);
    
    public Customer getCustomer();
    
    public void setCustomer(Customer customer);
    
    public Date getRatingSubmittedDate();
    
    public void setRatingSubmittedDate(Date ratingSubmittedDate);
    
    public RatingSummary getRatingSummary();
    
    public void setRatingSummary(RatingSummary ratingSummary);

}
