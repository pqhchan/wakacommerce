
package com.wakacommerce.core.pricing.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.pricing.service.exception.PricingException;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.Processor;
import com.wakacommerce.core.workflow.WorkflowException;

@Service("blPricingService")
public class PricingServiceImpl implements PricingService {

    @Resource(name="blPricingWorkflow")
    protected Processor pricingWorkflow;

    public Order executePricing(Order order) throws PricingException {
        try {
            ProcessContext<Order> context = (ProcessContext<Order>) pricingWorkflow.doActivities(order);
            Order response = context.getSeedData();

            return response;
        } catch (WorkflowException e) {
            throw new PricingException("Unable to execute pricing for order -- id: " + order.getId(), e);
        }
    }

}
