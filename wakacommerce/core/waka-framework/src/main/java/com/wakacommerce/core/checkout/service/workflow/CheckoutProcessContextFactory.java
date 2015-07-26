
package com.wakacommerce.core.checkout.service.workflow;

import com.wakacommerce.core.workflow.DefaultProcessContextImpl;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.ProcessContextFactory;
import com.wakacommerce.core.workflow.WorkflowException;

public class CheckoutProcessContextFactory implements ProcessContextFactory<CheckoutSeed, CheckoutSeed> {

    @Override
    public ProcessContext<CheckoutSeed> createContext(CheckoutSeed seedData) throws WorkflowException {
        ProcessContext<CheckoutSeed> context = new DefaultProcessContextImpl<CheckoutSeed>();
        context.setSeedData(seedData);

        return context;
    }

}
