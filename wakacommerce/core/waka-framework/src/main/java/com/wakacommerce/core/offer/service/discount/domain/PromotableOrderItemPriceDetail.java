
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.offer.domain.OfferItemCriteria;
import com.wakacommerce.core.offer.service.discount.PromotionDiscount;
import com.wakacommerce.core.offer.service.discount.PromotionQualifier;

import java.util.List;


public interface PromotableOrderItemPriceDetail {

    void addCandidateItemPriceDetailAdjustment(PromotableOrderItemPriceDetailAdjustment itemAdjustment);

    List<PromotableOrderItemPriceDetailAdjustment> getCandidateItemAdjustments();

    boolean hasNonCombinableAdjustments();

    boolean isTotalitarianOfferApplied();

    boolean isNonCombinableOfferApplied();

    void chooseSaleOrRetailAdjustments();

    void removeAllAdjustments();

    List<PromotionDiscount> getPromotionDiscounts();

    List<PromotionQualifier> getPromotionQualifiers();

    public int getQuantity();

    public void setQuantity(int quantity);

    PromotableOrderItem getPromotableOrderItem();

    int getQuantityAvailableToBeUsedAsQualifier(PromotableCandidateItemOffer itemOffer);

    int getQuantityAvailableToBeUsedAsTarget(PromotableCandidateItemOffer itemOffer);

    PromotionQualifier addPromotionQualifier(PromotableCandidateItemOffer itemOffer, OfferItemCriteria itemCriteria, int qtyToMarkAsQualifier);

    void addPromotionDiscount(PromotableCandidateItemOffer itemOffer, OfferItemCriteria itemCriteria, int qtyToMarkAsTarget);

    Money calculateItemUnitPriceWithAdjustments(boolean allowSalePrice);

    void finalizeQuantities();

    void clearAllNonFinalizedQuantities();

    String buildDetailKey();

    Money getFinalizedTotalWithAdjustments();

    Money calculateTotalAdjustmentValue();

    PromotableOrderItemPriceDetail splitIfNecessary();

    boolean useSaleAdjustments();

    boolean isAdjustmentsFinalized();

    void setAdjustmentsFinalized(boolean adjustmentsFinalized);

    PromotableOrderItemPriceDetail shallowCopy();

    PromotableOrderItemPriceDetail copyWithFinalizedData();

}
