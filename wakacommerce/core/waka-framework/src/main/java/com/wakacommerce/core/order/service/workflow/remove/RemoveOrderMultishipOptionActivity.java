
package com.wakacommerce.core.order.service.workflow.remove;

import javax.annotation.Resource;

import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.OrderMultishipOptionService;
import com.wakacommerce.core.order.service.workflow.CartOperationRequest;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

public class RemoveOrderMultishipOptionActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blOrderMultishipOptionService")
    protected OrderMultishipOptionService orderMultishipOptionService;

    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;

    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        Long orderItemId = request.getItemRequest().getOrderItemId();

        OrderItem orderItem = request.getOrderItem();
        if (orderItem instanceof BundleOrderItem) {
            for (OrderItem discrete : ((BundleOrderItem) orderItem).getDiscreteOrderItems()) {
                request.getMultishipOptionsToDelete().add(new Long[] { discrete.getId(), null });
            }
        } else {
            request.getMultishipOptionsToDelete().add(new Long[] { orderItemId, null });
        }
        
        return context;
    }

}
