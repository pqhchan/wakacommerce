
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentGroup;

public interface FulfillmentGroupAdjustment extends Adjustment {

    public FulfillmentGroup getFulfillmentGroup();

    public void init(FulfillmentGroup fulfillmentGroup, Offer offer, String reason);

    public void setValue(Money value);

    public void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);
    
}
