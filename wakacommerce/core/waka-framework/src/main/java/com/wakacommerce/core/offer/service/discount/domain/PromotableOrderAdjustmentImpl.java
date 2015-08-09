
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.service.type.OfferDiscountType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PromotableOrderAdjustmentImpl implements PromotableOrderAdjustment {
    
    private static final long serialVersionUID = 1L;
    
    protected PromotableCandidateOrderOffer promotableCandidateOrderOffer;
    protected PromotableOrder promotableOrder;
    protected Money adjustmentValue;
    protected Offer offer;
    
    protected boolean roundOfferValues = true;
    protected int roundingScale = 2;
    protected RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public PromotableOrderAdjustmentImpl(PromotableCandidateOrderOffer promotableCandidateOrderOffer, PromotableOrder promotableOrder) {
        assert (promotableOrder != null);
        assert (promotableCandidateOrderOffer != null);

        this.promotableCandidateOrderOffer = promotableCandidateOrderOffer;
        this.promotableOrder = promotableOrder;
        this.offer = promotableCandidateOrderOffer.getOffer();
        computeAdjustmentValue();
    }
    
    public PromotableOrderAdjustmentImpl(PromotableCandidateOrderOffer promotableCandidateOrderOffer,
            PromotableOrder promotableOrder, Money adjustmentValue) {
        this(promotableCandidateOrderOffer, promotableOrder);
        if (promotableOrder.isIncludeOrderAndItemAdjustments()) {
            this.adjustmentValue = adjustmentValue;
        }
    }

    @Override
    public PromotableOrder getPromotableOrder() {
        return promotableOrder;
    }
    
    @Override
    public Offer getOffer() {
        return offer;
    }
    
    /*
     * Calculates the value of the adjustment by first getting the current value of the order and then
     * calculating the value of this adjustment.   
     * 
     * If this adjustment value is greater than the currentOrderValue (e.g. would make the order go negative
     * then the adjustment value is set to the value of the order).  
     */
    protected void computeAdjustmentValue() {
        adjustmentValue = new Money(promotableOrder.getOrderCurrency());
        Money currentOrderValue = promotableOrder.calculateSubtotalWithAdjustments();
        
        // We also need to consider order offers that have been applied when figuring out if the current value of this
        // adjustment will be more than the current subtotal of the order
        currentOrderValue = currentOrderValue.subtract(promotableOrder.calculateOrderAdjustmentTotal());

        // Note: FIXED_PRICE not calculated as this is not a valid option for offers.
        if (offer.getDiscountType().equals(OfferDiscountType.AMOUNT_OFF)) {
            adjustmentValue = new Money(offer.getValue(), promotableOrder.getOrderCurrency());            
        } else if (offer.getDiscountType().equals(OfferDiscountType.PERCENT_OFF)) {
            BigDecimal offerValue = currentOrderValue.getAmount().multiply(offer.getValue().divide(new BigDecimal("100"), 5, RoundingMode.HALF_EVEN));
            
            if (isRoundOfferValues()) {
                offerValue = offerValue.setScale(roundingScale, roundingMode);
            }
            adjustmentValue = new Money(offerValue, promotableOrder.getOrderCurrency(), 5);
        }

        if (currentOrderValue.lessThan(adjustmentValue)) {
            adjustmentValue = currentOrderValue;
        }
    }
    
    @Override
    public Money getAdjustmentValue() {
        return adjustmentValue;
    }

    public boolean isRoundOfferValues() {
        return roundOfferValues;
    }

    public void setRoundingScale(int roundingScale) {
        this.roundingScale = roundingScale;
    }

    public int getRoundingScale() {
        return roundingScale;
    }

    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    @Override
    public boolean isCombinable() {
        Boolean combinable = offer.isCombinableWithOtherOffers();
        return (combinable != null && combinable);
    }

    @Override
    public boolean isTotalitarian() {
        Boolean totalitarian = offer.isTotalitarianOffer();
        return (totalitarian != null && totalitarian.booleanValue());
    }

}
