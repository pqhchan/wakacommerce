
package com.wakacommerce.core.order.service.workflow.remove;

import javax.annotation.Resource;

import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.core.order.service.workflow.CartOperationRequest;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

public class ValidateRemoveRequestActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;
    

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        OrderItemRequestDTO orderItemRequestDTO = request.getItemRequest();

        // Throw an exception if the user did not specify an orderItemId
        if (orderItemRequestDTO.getOrderItemId() == null) {
            throw new IllegalArgumentException("OrderItemId must be specified when removing from order");
        }

        // Throw an exception if the user did not specify an order to add the item to
        if (request.getOrder() == null) {
            throw new IllegalArgumentException("Order is required when updating item quantities");
        }
        
        // Throw an exception if the user is trying to remove an order item that is part of a bundle
        OrderItem orderItem = null;
        for (OrderItem oi : request.getOrder().getOrderItems()) {
            if (oi.getId().equals(orderItemRequestDTO.getOrderItemId())) {
                orderItem = oi;
            }
        }
        
        if (orderItem == null) {
            throw new IllegalArgumentException("Could not find order item to remove");
        }
        
        if (orderItem != null && orderItem instanceof DiscreteOrderItem) {
            DiscreteOrderItem doi = (DiscreteOrderItem) orderItem;
            if (doi.getBundleOrderItem() != null) {
                throw new IllegalArgumentException("Cannot remove an item that is part of a bundle");
            }
        }
        request.setOrderItem(orderItem);
        
        return context;
    }
    
}
