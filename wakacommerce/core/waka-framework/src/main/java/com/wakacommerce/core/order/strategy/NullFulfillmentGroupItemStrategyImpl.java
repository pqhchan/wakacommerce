
package com.wakacommerce.core.order.strategy;

import com.wakacommerce.core.order.service.workflow.CartOperationRequest;

/**
 *
 * @ hui
 */
public class NullFulfillmentGroupItemStrategyImpl extends FulfillmentGroupItemStrategyImpl {
    
    protected boolean removeEmptyFulfillmentGroups = false;
    
    @Override
    public CartOperationRequest onItemAdded(CartOperationRequest request) {
        return request;
    }
    
    @Override
    public CartOperationRequest onItemUpdated(CartOperationRequest request) {
        return request;
    }

    @Override
    public CartOperationRequest onItemRemoved(CartOperationRequest request) {
        return super.onItemRemoved(request);
    }
    
    @Override
    public CartOperationRequest verify(CartOperationRequest request) {
        return request;
    }
    
    @Override
    public boolean isRemoveEmptyFulfillmentGroups() {
        return removeEmptyFulfillmentGroups;
    }

}
