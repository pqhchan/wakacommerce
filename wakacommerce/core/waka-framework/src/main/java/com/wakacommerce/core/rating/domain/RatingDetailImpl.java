
package com.wakacommerce.core.rating.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.domain.CustomerImpl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_RATING_DETAIL")
public class RatingDetailImpl implements RatingDetail {

    @Id
    @GeneratedValue(generator = "RatingDetailId")
    @GenericGenerator(
        name="RatingDetailId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="RatingDetailImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.rating.domain.RatingDetailImpl")
        }
    )
    @Column(name = "RATING_DETAIL_ID")
    private Long id;

    @Column(name = "RATING", nullable = false)
    protected Double rating;

    @Column(name = "RATING_SUBMITTED_DATE", nullable = false)
    protected Date ratingSubmittedDate;

    @ManyToOne(targetEntity = CustomerImpl.class, optional = false)
    @JoinColumn(name = "CUSTOMER_ID")
    @Index(name="RATING_CUSTOMER_INDEX", columnNames={"CUSTOMER_ID"})
    protected Customer customer;

    @ManyToOne(optional = false, targetEntity = RatingSummaryImpl.class)
    @JoinColumn(name = "RATING_SUMMARY_ID")
    protected RatingSummary ratingSummary;

    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Double getRating() {
        return rating;
    }
    
    @Override
    public void setRating(Double newRating) {
        this.rating = newRating;
    }

    @Override
    public Date getRatingSubmittedDate() {
        return ratingSubmittedDate;
    }
    
    @Override
    public void setRatingSubmittedDate(Date ratingSubmittedDate) {
        this.ratingSubmittedDate = ratingSubmittedDate;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }
    
    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public RatingSummary getRatingSummary() {
        return ratingSummary;
    }
    
    @Override
    public void setRatingSummary(RatingSummary ratingSummary) {
        this.ratingSummary = ratingSummary;
    }
    
    
}
