
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentGroup;

import java.io.Serializable;

public interface CandidateFulfillmentGroupOffer extends Serializable {

    public Money getDiscountedPrice();

    public void setDiscountedPrice(Money discountedPrice);

    public FulfillmentGroup getFulfillmentGroup();

    public void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);
    
    public void setOffer(Offer offer);

    public Offer getOffer();
    
    public int getPriority();
    
}
