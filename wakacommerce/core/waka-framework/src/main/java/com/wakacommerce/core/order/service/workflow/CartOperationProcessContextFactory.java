
package com.wakacommerce.core.order.service.workflow;

import com.wakacommerce.core.workflow.DefaultProcessContextImpl;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.ProcessContextFactory;
import com.wakacommerce.core.workflow.WorkflowException;

/**
 *
 * @ hui
 */
public class CartOperationProcessContextFactory implements ProcessContextFactory<CartOperationRequest, CartOperationRequest> {

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
