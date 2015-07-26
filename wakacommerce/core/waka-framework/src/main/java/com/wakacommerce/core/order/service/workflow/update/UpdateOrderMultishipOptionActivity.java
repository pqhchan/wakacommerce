
package com.wakacommerce.core.order.service.workflow.update;

import org.springframework.util.CollectionUtils;

import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.OrderMultishipOptionService;
import com.wakacommerce.core.order.service.workflow.CartOperationRequest;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import javax.annotation.Resource;

public class UpdateOrderMultishipOptionActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blOrderMultishipOptionService")
    protected OrderMultishipOptionService orderMultishipOptionService;

    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;
    
    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        Long orderItemId = request.getItemRequest().getOrderItemId();
        
        Integer orderItemQuantityDelta = request.getOrderItemQuantityDelta();
        if (orderItemQuantityDelta < 0) {
            int numToDelete = -1 * orderItemQuantityDelta;
            //find the qty in the default fg
            OrderItem orderItem = request.getOrderItem();
            int qty = 0;
            if (!CollectionUtils.isEmpty(orderItem.getOrder().getFulfillmentGroups())) {
                FulfillmentGroup fg = orderItem.getOrder().getFulfillmentGroups().get(0);
                if (fg.getAddress() == null && fg.getFulfillmentOption() == null) {
                    for (FulfillmentGroupItem fgItem : fg.getFulfillmentGroupItems()) {
                        if (fgItem.getOrderItem().getId().equals(orderItemId)) {
                            qty += fgItem.getQuantity();
                        }
                    }
                }
            }
            if (numToDelete >= qty) {
                request.getMultishipOptionsToDelete().add(new Long[] { orderItemId, (long) (numToDelete - qty) });
            }
        }
        
        return context;
    }

}
