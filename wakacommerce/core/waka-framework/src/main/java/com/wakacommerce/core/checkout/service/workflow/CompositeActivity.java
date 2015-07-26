
package com.wakacommerce.core.checkout.service.workflow;

import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.Processor;

public class CompositeActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    private Processor workflow;

    /* (non-Javadoc)
     * @see com.wakacommerce.core.workflow.Activity#execute(com.wakacommerce.core.workflow.ProcessContext)
     */
    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        ProcessContext<CheckoutSeed> subContext = (ProcessContext<CheckoutSeed>) workflow.doActivities(context.getSeedData());
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
