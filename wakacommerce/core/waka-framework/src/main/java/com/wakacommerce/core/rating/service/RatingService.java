
package com.wakacommerce.core.rating.service;

import com.wakacommerce.core.rating.domain.RatingSummary;
import com.wakacommerce.core.rating.domain.ReviewDetail;
import com.wakacommerce.core.rating.service.type.RatingSortType;
import com.wakacommerce.core.rating.service.type.RatingType;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;
import java.util.Map;

public interface RatingService {

    public RatingSummary saveRatingSummary(RatingSummary rating);
    public void deleteRatingSummary(RatingSummary rating);
    public RatingSummary readRatingSummary(String itemId, RatingType type);
    public Map<String, RatingSummary> readRatingSummaries(List<String> itemIds, RatingType type);
    public void rateItem(String itemId, RatingType type, Customer customer, Double rating);

    public List<ReviewDetail> readReviews(String itemId, RatingType type, int start, int finish, RatingSortType sortBy);
    public void reviewItem(String itemId, RatingType type, Customer customer, Double rating, String reviewText);
    public void markReviewHelpful(Long reviewId, Customer customer, Boolean helpful);
    
    /**
     * Reads a ReviewDetail by the given customer and the itemId
     * @param itemId
     * @param customer
     * @return review, or null if review is not found
     */
    public ReviewDetail readReviewByCustomerAndItem(Customer customer, String itemId);

}
