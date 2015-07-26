
package com.wakacommerce.core.rating.domain;

import com.wakacommerce.profile.core.domain.Customer;

public interface ReviewFeedback {

    public Long getId();
    public Customer getCustomer();
    public ReviewDetail getReviewDetail();
    public Boolean getIsHelpful();
    public void setIsHelpful(Boolean isHelpful);
    public void setId(Long id);
    public void setCustomer(Customer customer);
    public void setReviewDetail(ReviewDetail reviewDetail);

}
