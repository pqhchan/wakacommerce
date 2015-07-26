
package com.wakacommerce.core.rating.domain;

import com.wakacommerce.core.rating.service.type.ReviewStatusType;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.Date;
import java.util.List;

public interface ReviewDetail {

    Long getId();
    Customer getCustomer();
    String getReviewText();
    void setReviewText(String reviewText);
    Date getReviewSubmittedDate();
    Integer helpfulCount();
    Integer notHelpfulCount();
    ReviewStatusType getStatus();
    RatingSummary getRatingSummary();
    RatingDetail getRatingDetail();
    List<ReviewFeedback> getReviewFeedback();

}
