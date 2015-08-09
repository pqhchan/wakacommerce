
package com.wakacommerce.core.order.service.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.CatalogService;
import com.wakacommerce.core.inventory.service.ContextualInventoryService;
import com.wakacommerce.core.inventory.service.InventoryUnavailableException;
import com.wakacommerce.core.inventory.service.type.InventoryType;
import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class CheckAvailabilityActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {

    private static final Log LOG = LogFactory.getLog(CheckAvailabilityActivity.class);
    
    @Resource(name = "blCatalogService")
    protected CatalogService catalogService;
    
    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;
    
    @Resource(name = "blInventoryService")
    protected ContextualInventoryService inventoryService;
    
    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        
        Sku sku;
        Long orderItemId = request.getItemRequest().getOrderItemId();
        if (orderItemId != null) {
            // this must be an update request as there is an order item ID available
            OrderItem orderItem = orderItemService.readOrderItemById(orderItemId);
            if (orderItem instanceof DiscreteOrderItem) {
                sku = ((DiscreteOrderItem) orderItem).getSku();
            } else if (orderItem instanceof BundleOrderItem) {
                sku = ((BundleOrderItem) orderItem).getSku();
            } else {
                LOG.warn("Could not check availability; did not recognize passed-in item " + orderItem.getClass().getName());
                return context;
            }
        } else {
            // No order item, this must be a new item add request
            Long skuId = request.getItemRequest().getSkuId();
            sku = catalogService.findSkuById(skuId);
        }
        
        
        // First check if this Sku is available
        if (!sku.isAvailable()) {
            throw new InventoryUnavailableException("The referenced Sku " + sku.getId() + " is marked as unavailable",
                    sku.getId(), request.getItemRequest().getQuantity(), 0);
        }
        
        if (InventoryType.CHECK_QUANTITY.equals(sku.getInventoryType())) {
            Integer requestedQuantity = request.getItemRequest().getQuantity();
            
            Map<String, Object> inventoryContext = new HashMap<String, Object>();
            inventoryContext.put(ContextualInventoryService.ORDER_KEY, context.getSeedData().getOrder());
            boolean available = inventoryService.isAvailable(sku, requestedQuantity, inventoryContext);
            if (!available) {
                throw new InventoryUnavailableException(sku.getId(),
                        requestedQuantity, inventoryService.retrieveQuantityAvailable(sku, inventoryContext));
            }
        }
        
        // the other case here is ALWAYS_AVAILABLE and null, which we are treating as being available
        
        return context;
    }
    
}
