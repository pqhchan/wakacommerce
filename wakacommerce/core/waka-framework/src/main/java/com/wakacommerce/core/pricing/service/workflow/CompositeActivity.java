
package com.wakacommerce.core.pricing.service.workflow;

import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.Processor;

public class CompositeActivity extends BaseActivity<ProcessContext<Order>> {

    private Processor workflow;

    @Override
    public ProcessContext<Order> execute(ProcessContext<Order> context) throws Exception {
        ProcessContext<Order> subContext = (ProcessContext<Order>) workflow.doActivities(context.getSeedData());
        if (subContext.isStopped()) {
            context.stopProcess();
        }

        return context;
    }

    public Processor getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Processor workflow) {
        this.workflow = workflow;
    }

}
