
package com.wakacommerce.core.rating.dao;

import com.wakacommerce.core.rating.domain.ReviewDetail;
import com.wakacommerce.core.rating.domain.ReviewFeedback;
import com.wakacommerce.profile.core.domain.Customer;

public interface ReviewDetailDao {

    ReviewDetail readReviewDetailById(Long reviewId);
    ReviewDetail saveReviewDetail(ReviewDetail reviewDetail);
    ReviewDetail create();
    ReviewFeedback createFeedback();
    ReviewDetail readReviewByCustomerAndItem(Customer customer, String itemId);

}
