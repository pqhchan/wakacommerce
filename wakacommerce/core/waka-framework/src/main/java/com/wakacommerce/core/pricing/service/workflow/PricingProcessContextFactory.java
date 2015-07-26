
package com.wakacommerce.core.pricing.service.workflow;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.workflow.DefaultProcessContextImpl;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.ProcessContextFactory;
import com.wakacommerce.core.workflow.WorkflowException;

public class PricingProcessContextFactory implements ProcessContextFactory<Order, Order> {

    @Override
    public ProcessContext<Order> createContext(Order seedData) throws WorkflowException {
        ProcessContext<Order> context = new DefaultProcessContextImpl<Order>();
        context.setSeedData(seedData);

        return context;
    }

}
