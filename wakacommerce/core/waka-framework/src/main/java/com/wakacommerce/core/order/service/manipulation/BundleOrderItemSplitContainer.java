
package com.wakacommerce.core.order.service.manipulation;

import java.util.ArrayList;
import java.util.List;

import com.wakacommerce.core.order.domain.BundleOrderItem;

public class BundleOrderItemSplitContainer {
    
    protected BundleOrderItem key;
    protected List<BundleOrderItem> splitItems = new ArrayList<BundleOrderItem>();
    
    public BundleOrderItem getKey() {
        return key;
    }
    
    public void setKey(BundleOrderItem key) {
        this.key = key;
    }
    
    public List<BundleOrderItem> getSplitItems() {
        return splitItems;
    }
    
    public void setSplitItems(List<BundleOrderItem> splitItems) {
        this.splitItems = splitItems;
    }

}
