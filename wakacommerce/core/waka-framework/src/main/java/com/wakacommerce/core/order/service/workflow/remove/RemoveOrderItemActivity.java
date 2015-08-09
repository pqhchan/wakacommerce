
package com.wakacommerce.core.order.service.workflow.remove;

import org.apache.commons.collections.CollectionUtils;

import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.order.service.workflow.CartOperationRequest;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.util.List;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class RemoveOrderItemActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;
    
    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();

        OrderItem orderItem = request.getOrderItem();
        removeItemAndChildren(request.getOisToDelete(), orderItem);
        
        return context;
    }
    
    protected void removeItemAndChildren(List<OrderItem> oisToDelete, OrderItem orderItem) {
        if (CollectionUtils.isNotEmpty(orderItem.getChildOrderItems())) {
            for (OrderItem childOrderItem : orderItem.getChildOrderItems()) {
                removeItemAndChildren(oisToDelete, childOrderItem);
            }
        }
        
        oisToDelete.add(orderItem);
    }

}
