
package com.wakacommerce.core.rating.service;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.core.rating.dao.RatingSummaryDao;
import com.wakacommerce.core.rating.dao.ReviewDetailDao;
import com.wakacommerce.core.rating.domain.RatingDetail;
import com.wakacommerce.core.rating.domain.RatingSummary;
import com.wakacommerce.core.rating.domain.ReviewDetail;
import com.wakacommerce.core.rating.domain.ReviewDetailImpl;
import com.wakacommerce.core.rating.domain.ReviewFeedback;
import com.wakacommerce.core.rating.service.type.RatingSortType;
import com.wakacommerce.core.rating.service.type.RatingType;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service("blRatingService")
public class RatingServiceImpl implements RatingService {

    @Resource(name="blRatingSummaryDao")
    protected RatingSummaryDao ratingSummaryDao;

    @Resource(name="blReviewDetailDao")
    protected ReviewDetailDao reviewDetailDao;

    @Override
    @Transactional("blTransactionManager")
    public void deleteRatingSummary(RatingSummary ratingSummary) {
        ratingSummaryDao.deleteRatingSummary(ratingSummary);
    }

    @Override
    @Transactional("blTransactionManager")
    public void markReviewHelpful(Long reviewId, Customer customer, Boolean helpful) {
        ReviewDetail reviewDetail = reviewDetailDao.readReviewDetailById(reviewId);

        if (reviewDetail != null) {
            ReviewFeedback reviewFeedback = reviewDetailDao.createFeedback();
            reviewFeedback.setCustomer(customer);
            reviewFeedback.setIsHelpful(helpful);
            reviewFeedback.setReviewDetail(reviewDetail);
            reviewDetail.getReviewFeedback().add(reviewFeedback);
            reviewDetailDao.saveReviewDetail(reviewDetail);
        }

    }

    @Override
    @Transactional("blTransactionManager")
    public void rateItem(String itemId, RatingType type, Customer customer, Double rating) {
        RatingSummary ratingSummary = this.readRatingSummary(itemId, type);

        if (ratingSummary == null) {
            ratingSummary = ratingSummaryDao.createSummary(itemId, type);
        }

        RatingDetail ratingDetail = ratingSummaryDao.readRating(customer.getId(), ratingSummary.getId());

        if (ratingDetail == null) {
            ratingDetail = ratingSummaryDao.createDetail(ratingSummary, rating, SystemTime.asDate(), customer);
        }

        ratingDetail.setRating(rating);

        ratingSummary.getRatings().add(ratingDetail);
        ratingSummaryDao.saveRatingSummary(ratingSummary);
    }

    @Override
    public RatingSummary readRatingSummary(String itemId, RatingType type) {
        return ratingSummaryDao.readRatingSummary(itemId, type);
    }

    @Override
    public Map<String, RatingSummary> readRatingSummaries(List<String> itemIds, RatingType type) {
        List<RatingSummary> ratings = ratingSummaryDao.readRatingSummaries(itemIds, type);
        Map<String, RatingSummary> ratingsMap = new HashMap<String, RatingSummary>();

        for (RatingSummary ratingSummary : ratings) {
            ratingsMap.put(ratingSummary.getItemId(), ratingSummary);
        }

        return ratingsMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ReviewDetail> readReviews(String itemId, RatingType type, int start, int finish, RatingSortType sortBy) {
        RatingSummary summary = this.readRatingSummary(itemId, type);
        if(summary != null) {
            List<ReviewDetail> reviews = summary.getReviews();
            List<ReviewDetail> reviewsToReturn = new ArrayList<ReviewDetail>();
            int i = 0;
            for (ReviewDetail review : reviews) {
                if (i > finish) {
                    break;
                }
    
                if (i >= start) {
                    reviewsToReturn.add(review);
                }
    
                i++;
            }
    
            String sortByBeanProperty = "reviewSubmittedDate";
            if (sortBy == RatingSortType.MOST_HELPFUL) {
                sortByBeanProperty = "helpfulCount";
            }
    
            Collections.sort(reviewsToReturn, new BeanComparator(sortByBeanProperty));
    
            return reviewsToReturn;
        } else {
            return new ArrayList<ReviewDetail>();
        }
    }

    @Override
    @Transactional("blTransactionManager")
    public RatingSummary saveRatingSummary(RatingSummary ratingSummary) {
        return ratingSummaryDao.saveRatingSummary(ratingSummary);
    }

    @Override
    @Transactional("blTransactionManager")
    public void reviewItem(String itemId, RatingType type, Customer customer, Double rating, String reviewText) {
        RatingSummary ratingSummary = this.readRatingSummary(itemId, type);

        if (ratingSummary == null) {
            ratingSummary = ratingSummaryDao.createSummary(itemId, type);
        }

        RatingDetail ratingDetail = ratingSummaryDao.readRating(customer.getId(), ratingSummary.getId());

        if (ratingDetail == null) {
            ratingDetail = ratingSummaryDao.createDetail(ratingSummary, rating, SystemTime.asDate(), customer);
        } else {
            ratingDetail.setRating(rating);         
        }

        ratingSummary.getRatings().add(ratingDetail);

        ReviewDetail reviewDetail = ratingSummaryDao.readReview(customer.getId(), ratingSummary.getId());

        if (reviewDetail == null) {
            reviewDetail = new ReviewDetailImpl(customer, SystemTime.asDate(), ratingDetail, reviewText, ratingSummary);
        } else {
            reviewDetail.setReviewText(reviewText);         
        }

        ratingSummary.getReviews().add(reviewDetail);
        // load reviews
        ratingSummary.getReviews().size();
        ratingSummaryDao.saveRatingSummary(ratingSummary);
    }
    
    @Override
    public ReviewDetail readReviewByCustomerAndItem(Customer customer, String itemId) {
        return reviewDetailDao.readReviewByCustomerAndItem(customer, itemId);
    }

}
