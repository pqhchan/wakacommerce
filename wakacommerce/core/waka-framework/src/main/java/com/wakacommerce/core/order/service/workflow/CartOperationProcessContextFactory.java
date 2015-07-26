
package com.wakacommerce.core.order.service.workflow;

import com.wakacommerce.core.workflow.DefaultProcessContextImpl;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.ProcessContextFactory;
import com.wakacommerce.core.workflow.WorkflowException;

/**
 * Provides a method that creates the seed ProcessContext object for a cart
 * operation. The same seed object is used for add item, update item, and remove
 * item cart operations.
 * 
 *apazzolini
 */
public class CartOperationProcessContextFactory implements ProcessContextFactory<CartOperationRequest, CartOperationRequest> {

    /**
     * Creates the necessary context for cart operations
     */
    public ProcessContext<CartOperationRequest> createContext(CartOperationRequest seedData) throws WorkflowException {
        if (!(seedData instanceof CartOperationRequest)){
            throw new WorkflowException("Seed data instance is incorrect. " +
                    "Required class is " + CartOperationRequest.class.getName() + " " +
                    "but found class: " + seedData.getClass().getName());
        }
        
        ProcessContext<CartOperationRequest> context = new DefaultProcessContextImpl<CartOperationRequest>();
        context.setSeedData((CartOperationRequest) seedData);
        return context;
    }

}
