
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.Offer;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface PromotableOrderItemPriceDetailAdjustment extends Serializable {

    PromotableOrderItemPriceDetail getPromotableOrderItemPriceDetail();

    Offer getOffer();

    Money getRetailAdjustmentValue();

    Money getSaleAdjustmentValue();

    Money getAdjustmentValue();

    boolean isAppliedToSalePrice();

    boolean isCombinable();

    boolean isTotalitarian();

    Long getOfferId();

    void finalizeAdjustment(boolean useSalePrice);

    public PromotableOrderItemPriceDetailAdjustment copy();

}
