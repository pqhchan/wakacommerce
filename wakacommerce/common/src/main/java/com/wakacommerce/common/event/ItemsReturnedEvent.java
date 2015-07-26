
package com.wakacommerce.common.event;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

public class ItemsReturnedEvent extends BroadleafApplicationEvent {

    private static final long serialVersionUID = 1L;

    protected final Map<Long, Integer> itemsAndQuantitiesReturned;

    public ItemsReturnedEvent(Long orderId, Map<Long, Integer> returnedItems) {
        super(orderId);
        Assert.notNull(orderId);
        Assert.notEmpty(returnedItems);
        this.itemsAndQuantitiesReturned = Collections.unmodifiableMap(returnedItems);
    }

    public Long getOrderId() {
        return (Long) super.getSource();
    }

    public Map<Long, Integer> getItemsAndQuantitiesReturned() {
        return itemsAndQuantitiesReturned;
    }

}
