
package com.wakacommerce.core.rating.dao;

import com.wakacommerce.core.rating.domain.RatingDetail;
import com.wakacommerce.core.rating.domain.RatingSummary;
import com.wakacommerce.core.rating.domain.ReviewDetail;
import com.wakacommerce.core.rating.service.type.RatingType;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.Date;
import java.util.List;

public interface RatingSummaryDao {

    public RatingSummary createSummary();
    
    public RatingSummary createSummary(String itemId, RatingType type);
    
    public RatingDetail createDetail();
    
    public RatingDetail createDetail(RatingSummary ratingSummary, Double rating, Date submittedDate, Customer customer);
    
    RatingSummary readRatingSummary(String itemId, RatingType type);
    List<RatingSummary> readRatingSummaries(List<String> itemIds, RatingType type);
    RatingSummary saveRatingSummary(RatingSummary summary);
    void deleteRatingSummary(RatingSummary summary);

    RatingDetail readRating(Long customerId, Long ratingSummaryId);
    ReviewDetail readReview(Long customerId, Long ratingSummaryId);
}
