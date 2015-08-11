package com.wakacommerce.core.web.controller.catalog;

import org.springframework.ui.Model;

import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.rating.domain.ReviewDetail;
import com.wakacommerce.core.rating.service.RatingService;
import com.wakacommerce.core.rating.service.type.RatingType;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class WakaRatingsController {

    @Resource(name = "blRatingService")
    protected RatingService ratingService;
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;
    
    protected String formView = "catalog/partials/review";
    protected String successView = "catalog/partials/reviewSuccessful";
    
    
    public String viewReviewForm(HttpServletRequest request, Model model, ReviewForm form, String itemId) {
        Product product = catalogService.findProductById(Long.valueOf(itemId));
        form.setProduct(product);
        ReviewDetail reviewDetail = ratingService.readReviewByCustomerAndItem(CustomerState.getCustomer(), itemId);
        if (reviewDetail != null) {
            form.setReviewText(reviewDetail.getReviewText());
            form.setRating(reviewDetail.getRatingDetail().getRating());
        }
        model.addAttribute("reviewForm", form);
        return getFormView();
    }
    
    public String reviewItem(HttpServletRequest request, Model model, ReviewForm form, String itemId) {
        ratingService.reviewItem(itemId, RatingType.PRODUCT, CustomerState.getCustomer(), form.getRating(), form.getReviewText());
        model.addAttribute("reviewForm", form);
        return getSuccessView();
    }
    
    public String getFormView() {
        return formView;
    }
    
    public String getSuccessView() {
        return successView;
    }
    
}
