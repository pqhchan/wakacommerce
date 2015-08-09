
package com.wakacommerce.core.order.strategy;

import com.wakacommerce.core.order.service.workflow.CartOperationRequest;
import com.wakacommerce.core.pricing.service.exception.PricingException;

/**
 *
 * @ hui
 */
public interface FulfillmentGroupItemStrategy {

    public CartOperationRequest onItemAdded(CartOperationRequest request) throws PricingException;

    public CartOperationRequest onItemUpdated(CartOperationRequest request) throws PricingException;
    
    public CartOperationRequest onItemRemoved(CartOperationRequest request) throws PricingException;
    
    public CartOperationRequest verify(CartOperationRequest request) throws PricingException;

    public void setRemoveEmptyFulfillmentGroups(boolean removeEmptyFulfillmentGroups);
    public boolean isRemoveEmptyFulfillmentGroups();

}
