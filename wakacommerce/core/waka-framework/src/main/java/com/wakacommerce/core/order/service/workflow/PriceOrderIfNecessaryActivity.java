
package com.wakacommerce.core.order.service.workflow;

import org.apache.commons.collections.CollectionUtils;

import com.wakacommerce.core.order.dao.FulfillmentGroupItemDao;
import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.OrderMultishipOptionService;
import com.wakacommerce.core.order.service.OrderService;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class PriceOrderIfNecessaryActivity extends BaseActivity<ProcessContext<CartOperationRequest>> {
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;
    
    @Resource(name = "blOrderItemService")
    protected OrderItemService orderItemService;

    @Resource(name = "blFulfillmentGroupItemDao")
    protected FulfillmentGroupItemDao fgItemDao;

    @Resource(name = "blOrderMultishipOptionService")
    protected OrderMultishipOptionService orderMultishipOptionService;
    
    @Override
    public ProcessContext<CartOperationRequest> execute(ProcessContext<CartOperationRequest> context) throws Exception {
        CartOperationRequest request = context.getSeedData();
        Order order = request.getOrder();

        // If the UpdateOrderMultishipOptionActivity identified that we should delete order item multiship options,
        // go ahead and carry out that delete here.
        if (CollectionUtils.isNotEmpty(request.getMultishipOptionsToDelete())) {
            for (Long[] pack : request.getMultishipOptionsToDelete()) {
                if (pack[1] == null) {
                    orderMultishipOptionService.deleteOrderItemOrderMultishipOptions(pack[0]);
                } else {
                    orderMultishipOptionService.deleteOrderItemOrderMultishipOptions(pack[0], pack[1].intValue());
                }
            }
        }
        
        // We potentially have some FulfillmentGroupItems that were identified in the FulfillmentGroupItemStrategy as
        // ones that should be deleted. Delete them here.
        if (CollectionUtils.isNotEmpty(request.getFgisToDelete())) {
            for (FulfillmentGroupItem fgi : request.getFgisToDelete()) {
                for (FulfillmentGroup fg : order.getFulfillmentGroups()) {
                    ListIterator<FulfillmentGroupItem> fgItemIter = fg.getFulfillmentGroupItems().listIterator();
                    while (fgItemIter.hasNext()) {
                        FulfillmentGroupItem fgi2 = fgItemIter.next();
                        if (fgi2 == fgi) {
                            fgItemIter.remove();
                            fgItemDao.delete(fgi2);
                        }
                    }
                }
            }
        }
        
        // We now need to delete any OrderItems that were marked as such, including their children, if any
        for (OrderItem oi : request.getOisToDelete()) {
            order.getOrderItems().remove(oi);
            orderItemService.delete(oi);
            
            if (oi.getParentOrderItem() != null) {
                OrderItem parentItem = oi.getParentOrderItem();
                parentItem.getChildOrderItems().remove(oi);
            }
        }
        
        // We need to build up a map of OrderItem to which FulfillmentGroupItems reference that particular OrderItem.
        // We'll also save the order item and build up a map of the unsaved items to their saved counterparts.
        Map<OrderItem, List<FulfillmentGroupItem>> oiFgiMap = new HashMap<OrderItem, List<FulfillmentGroupItem>>();
        Map<OrderItem, OrderItem> savedOrderItems = new HashMap<OrderItem, OrderItem>();
        for (OrderItem oi : order.getOrderItems()) {
            if (oi instanceof BundleOrderItem) {
                // We first need to save the discrete order items that are part of this bundle. Once they're saved, we'll
                // mark them and remove them from this bundle.
                List<DiscreteOrderItem> doisToAdd = new ArrayList<DiscreteOrderItem>();
                ListIterator<DiscreteOrderItem> li = ((BundleOrderItem) oi ).getDiscreteOrderItems().listIterator();
                while (li.hasNext()) {
                    DiscreteOrderItem doi = li.next();
                    getOiFgiMap(order, oiFgiMap, doi);
                    DiscreteOrderItem savedDoi = (DiscreteOrderItem) orderItemService.saveOrderItem(doi); 
                    savedOrderItems.put(doi, savedDoi);
                    li.remove();
                    doisToAdd.add(savedDoi);
                }
                
                // After the discrete order items are saved, we can re-add the saved versions to our bundle and then
                // save the bundle as well.
                ((BundleOrderItem) oi).getDiscreteOrderItems().addAll(doisToAdd);
                BundleOrderItem savedBoi = (BundleOrderItem) orderItemService.saveOrderItem(oi);
                savedOrderItems.put(oi, savedBoi);
                
                // Lastly, we'll want to go through our saved discrete order items and update the bundle that they relate
                // to to the saved version of the bundle.
                for (DiscreteOrderItem doi : savedBoi.getDiscreteOrderItems()) {
                    doi.setBundleOrderItem(savedBoi);
                }
            } else {
                getOiFgiMap(order, oiFgiMap, oi);
                savedOrderItems.put(oi, orderItemService.saveOrderItem(oi));
            }
        }
        
        // Now, we'll update the orderitems in the order to their saved counterparts
        ListIterator<OrderItem> li = order.getOrderItems().listIterator();
        List<OrderItem> oisToAdd = new ArrayList<OrderItem>();
        while (li.hasNext()) {
            OrderItem oi = li.next();
            OrderItem savedOi = savedOrderItems.get(oi);
            oisToAdd.add(savedOi);
            li.remove();
        }
        order.getOrderItems().addAll(oisToAdd);

        for (Entry<OrderItem, List<FulfillmentGroupItem>> entry : oiFgiMap.entrySet()) {
            // Update any FulfillmentGroupItems that reference order items
            for (FulfillmentGroupItem fgi : entry.getValue()) {
                fgi.setOrderItem(savedOrderItems.get(entry.getKey()));
            }

            // We also need to update the orderItem on the request in case it's used by the caller of this workflow
            if (entry.getKey() == request.getOrderItem()) {
                request.setOrderItem(savedOrderItems.get(entry.getKey()));
            }
        }
        
        // We need to add the new item to the parent's child order items as well.
        for (OrderItem oi : order.getOrderItems()) {
            if (oi.getId().equals(request.getItemRequest().getParentOrderItemId())) {
                oi.getChildOrderItems().add(request.getOrderItem());
            }
        }

        // If a custom implementation needs to handle additional saves before the parent Order is saved, this method
        // can be overridden to provide that functionality.
        preSaveOperation(request);
        
        // Now that our collection items in our Order have been saved and the state of our Order is in a place where we
        // won't get a transient save exception, we are able to go ahead and save the order with optional pricing.
        order = orderService.save(order, request.isPriceOrder());
        request.setOrder(order);
        
        return context;
    }

    protected void getOiFgiMap(Order order, Map<OrderItem, List<FulfillmentGroupItem>> oiFgiMap, OrderItem oi) {
        List<FulfillmentGroupItem> fgis = new ArrayList<FulfillmentGroupItem>();

        for (FulfillmentGroup fg : order.getFulfillmentGroups()) {
            for (FulfillmentGroupItem fgi : fg.getFulfillmentGroupItems()) {
                if (fgi.getOrderItem().equals(oi)) {
                    fgis.add(fgi);
                }
            }
        }

        oiFgiMap.put(oi, fgis);
    }

    protected void preSaveOperation(CartOperationRequest request) {
        // Broadleaf implementation does nothing here
    }

}
