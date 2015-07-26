
package com.wakacommerce.common.event;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

public class ItemsCancelledEvent extends BroadleafApplicationEvent {

    private static final long serialVersionUID = 1L;

    protected final Map<Long, Integer> itemsAndQuantitiesCancelled;

    public ItemsCancelledEvent(Long fulfillmentGroupId, Map<Long, Integer> cancelledItems) {
        super(fulfillmentGroupId);
        Assert.notNull(fulfillmentGroupId);
        Assert.notEmpty(cancelledItems);
        this.itemsAndQuantitiesCancelled = Collections.unmodifiableMap(cancelledItems);
    }

    public Long getFulfillmentGroupId() {
        return (Long) super.getSource();
    }

    public Map<Long, Integer> getItemsAndQuantitiesCancelled() {
        return itemsAndQuantitiesCancelled;
    }
}
