
package com.wakacommerce.core.order.service.workflow.remove;

import javax.annotation.Resource;

import com.wakacommerce.core.order.service.workflow.CartOperationRequest;
import com.wakacommerce.core.order.strategy.FulfillmentGroupItemStrategy;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

public class RemoveFulfillmentGroupItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blFulfillmentGroupItemStrategy")
    protected FulfillmentGroupItemStrategy fgItemStrategy;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        
        request = fgItemStrategy.onItemRemoved(request);
        
        context.setSeedData(request);
        return context;
    }

}
