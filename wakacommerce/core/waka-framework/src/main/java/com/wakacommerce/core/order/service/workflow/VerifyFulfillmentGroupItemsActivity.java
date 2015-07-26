
package com.wakacommerce.core.order.service.workflow;

import javax.annotation.Resource;

import com.wakacommerce.core.order.strategy.FulfillmentGroupItemStrategy;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

public class VerifyFulfillmentGroupItemsActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blFulfillmentGroupItemStrategy")
    protected FulfillmentGroupItemStrategy fgItemStrategy;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        
        request = fgItemStrategy.verify(request);
        
        context.setSeedData(request);
        return context;
    }

}
