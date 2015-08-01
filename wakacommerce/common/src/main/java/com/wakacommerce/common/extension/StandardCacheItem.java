
package com.wakacommerce.common.extension;

import java.io.Serializable;

/**
 * Represents a member of a query result list for a multitenant sparsely populated cache scenario (see {@link com.wakacommerce.common.extension.SparselyPopulatedQueryExtensionHandler}).
 * Denotes whether the item is a normal/active item in a standard site, or if it's a deleted/archived item in a standard site.
 *
 * 
 */
public class StandardCacheItem implements Serializable {

    private Object cacheItem;
    private ItemStatus itemStatus;

    public Object getCacheItem() {
        return cacheItem;
    }

    public void setCacheItem(Object cacheItem) {
        this.cacheItem = cacheItem;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

}
