
package com.wakacommerce.core.offer.service.discount.domain;

import java.math.RoundingMode;

/**
 *
 * @ hui
 */
public interface PromotionRounding {

    boolean isRoundOfferValues();

    RoundingMode getRoundingMode();

    int getRoundingScale();
    
}
