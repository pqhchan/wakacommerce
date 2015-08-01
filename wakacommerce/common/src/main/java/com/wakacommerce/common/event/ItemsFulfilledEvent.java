
package com.wakacommerce.common.event;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

/**
 * Event that may be raised to indicate that items have been fulfilled.
 * 
 *  
 *
 */
public class ItemsFulfilledEvent extends BroadleafApplicationEvent {

    private static final long serialVersionUID = 1L;

    protected final Map<Long, Integer> itemsAndQuantitiesFulfilled;

    public ItemsFulfilledEvent(Long fulfillmentGroupId, Map<Long, Integer> fulfilled) {
        super(fulfillmentGroupId);
        Assert.notNull(fulfillmentGroupId);
        Assert.notEmpty(fulfilled);
        this.itemsAndQuantitiesFulfilled = Collections.unmodifiableMap(fulfilled);
    }

    public Long getFulfillmentGroupId() {
        return (Long) super.getSource();
    }

    public Map<Long, Integer> getItemsAndQuantitiesFulfilled() {
        return itemsAndQuantitiesFulfilled;
    }
}
