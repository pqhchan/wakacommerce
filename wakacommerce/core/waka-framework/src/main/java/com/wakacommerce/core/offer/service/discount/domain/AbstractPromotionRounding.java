
package com.wakacommerce.core.offer.service.discount.domain;

import java.math.RoundingMode;


public abstract class AbstractPromotionRounding implements PromotionRounding {

    protected boolean roundOfferValues = true;
    protected int roundingScale = 2;
    protected RoundingMode roundingMode = RoundingMode.HALF_EVEN;

    public boolean isRoundOfferValues() {
        return roundOfferValues;
    }

    public void setRoundingScale(int roundingScale) {
        this.roundingScale = roundingScale;
    }

    public void setRoundingMode(RoundingMode roundingMode) {
        this.roundingMode = roundingMode;
    }

    @Override
    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    @Override
    public int getRoundingScale() {
        return roundingScale;
    }

}
