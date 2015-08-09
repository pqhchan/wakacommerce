
package com.wakacommerce.core.web.processor;

import org.apache.commons.lang.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.core.rating.domain.RatingSummary;
import com.wakacommerce.core.rating.domain.ReviewDetail;
import com.wakacommerce.core.rating.service.RatingService;
import com.wakacommerce.core.rating.service.type.RatingType;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class RatingsProcessor extends AbstractModelVariableModifierProcessor {
    
    @Resource(name = "blRatingService")
    protected RatingService ratingService;

    public RatingsProcessor() {
        super("ratings");
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue("itemId"));
        String itemId = String.valueOf(expression.execute(arguments.getConfiguration(), arguments));
        RatingSummary ratingSummary = ratingService.readRatingSummary(itemId, RatingType.PRODUCT);
        if (ratingSummary != null) {
            addToModel(arguments, getRatingsVar(element), ratingSummary);
        }
        
        Customer customer = CustomerState.getCustomer();
        ReviewDetail reviewDetail = null;
        if (!customer.isAnonymous()) {
            reviewDetail = ratingService.readReviewByCustomerAndItem(customer, itemId);
        }
        if (reviewDetail != null) {
            addToModel(arguments, "currentCustomerReview", reviewDetail);
        }
        
    }
    
    private String getRatingsVar(Element element) {
        String ratingsVar = element.getAttributeValue("ratingsVar");
        if (StringUtils.isNotEmpty(ratingsVar)) {
            return ratingsVar;
        } 
        return "ratingSummary";
    }

}
