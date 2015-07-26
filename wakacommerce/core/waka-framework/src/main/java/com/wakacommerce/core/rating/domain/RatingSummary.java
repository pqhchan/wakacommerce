
package com.wakacommerce.core.rating.domain;

import java.util.List;

import com.wakacommerce.core.rating.service.type.RatingType;

public interface RatingSummary {
    
    public Long getId();
    
    public void setId(Long id);
    
    public RatingType getRatingType();
    
    public void setRatingType(RatingType ratingType);
    
    public String getItemId();
    
    public void setItemId(String itemId);
    
    public Integer getNumberOfRatings();
    
    public Integer getNumberOfReviews();
    
    public Double getAverageRating();
    
    public void resetAverageRating();

    public List<ReviewDetail> getReviews();
    
    public void setReviews(List<ReviewDetail> reviews);
    
    public List<RatingDetail> getRatings();
    
    public void setRatings(List<RatingDetail> ratings);

}
