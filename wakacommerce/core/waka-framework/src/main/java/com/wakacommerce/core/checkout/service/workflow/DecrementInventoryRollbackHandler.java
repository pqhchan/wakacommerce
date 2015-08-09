
package com.wakacommerce.core.checkout.service.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.inventory.service.ContextualInventoryService;
import com.wakacommerce.core.inventory.service.InventoryUnavailableException;
import com.wakacommerce.core.workflow.Activity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.state.RollbackFailureException;
import com.wakacommerce.core.workflow.state.RollbackHandler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Component("blDecrementInventoryRollbackHandler")
public class DecrementInventoryRollbackHandler implements RollbackHandler<CheckoutSeed>{

    private static final Log LOG = LogFactory.getLog(DecrementInventoryRollbackHandler.class);
    
    public static final String ROLLBACK_BLC_INVENTORY_DECREMENTED = "ROLLBACK_BLC_INVENTORY_DECREMENTED";
    public static final String ROLLBACK_BLC_INVENTORY_INCREMENTED = "ROLLBACK_BLC_INVENTORY_INCREMENTED";
    public static final String ROLLBACK_BLC_ORDER_ID = "ROLLBACK_BLC_ORDER_ID";
    public static final String EXTENDED_ROLLBACK_STATE = "BLC_EXTENDED_ROLLBACK_STATE";

    @Resource(name = "blInventoryService")
    protected ContextualInventoryService inventoryService;
    
    @Override
    public void rollbackState(Activity<? extends ProcessContext<CheckoutSeed>> activity, ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration)
            throws RollbackFailureException {

        if (shouldExecute(activity, processContext, stateConfiguration)) {

            String orderId = "(Not Known)";
            if (stateConfiguration.get(ROLLBACK_BLC_ORDER_ID) != null) {
                orderId = String.valueOf(stateConfiguration.get(ROLLBACK_BLC_ORDER_ID));
            }
            
            @SuppressWarnings("unchecked")
            Map<Sku, Integer> inventoryToIncrement = (Map<Sku, Integer>) stateConfiguration.get(ROLLBACK_BLC_INVENTORY_DECREMENTED);
            @SuppressWarnings("unchecked")
            Map<Sku, Integer> inventoryToDecrement = (Map<Sku, Integer>) stateConfiguration.get(ROLLBACK_BLC_INVENTORY_INCREMENTED);
            
            Map<String, Object> contextualInformation = new HashMap<String, Object>();
            contextualInformation.put(ContextualInventoryService.ROLLBACK_STATE_KEY, stateConfiguration.get(EXTENDED_ROLLBACK_STATE));
            contextualInformation.put(ContextualInventoryService.ORDER_KEY, processContext.getSeedData().getOrder());
            if (inventoryToIncrement != null && !inventoryToIncrement.isEmpty()) {
                try {
                    inventoryService.incrementInventory(inventoryToIncrement, contextualInformation);
                } catch (Exception ex) {
                    RollbackFailureException rfe = new RollbackFailureException("An unexpected error occured in the error handler of the checkout workflow trying to compensate for inventory. This happend for order ID: " +
                            orderId + ". This should be corrected manually!", ex);
                    rfe.setActivity(activity);
                    rfe.setProcessContext(processContext);
                    rfe.setStateItems(stateConfiguration);
                    throw rfe;
                }
            }
    
            if (inventoryToDecrement != null && !inventoryToDecrement.isEmpty()) {
                try {
                    inventoryService.decrementInventory(inventoryToDecrement, contextualInformation);
                } catch (InventoryUnavailableException e) {
                    //This is an awkward, unlikely state.  I just added some inventory, but something happened, and I want to remove it, but it's already gone!
                    RollbackFailureException rfe = new RollbackFailureException("While trying roll back (decrement) inventory, we found that there was none left decrement.", e);
                    rfe.setActivity(activity);
                    rfe.setProcessContext(processContext);
                    rfe.setStateItems(stateConfiguration);
                    throw rfe;
                } catch (RuntimeException ex) {
                    LOG.error("An unexpected error occured in the error handler of the checkout workflow trying to compensate for inventory. This happend for order ID: " +
                            orderId + ". This should be corrected manually!", ex);
                    RollbackFailureException rfe = new RollbackFailureException("An unexpected error occured in the error handler of the checkout workflow " +
                            "trying to compensate for inventory. This happend for order ID: " +
                            orderId + ". This should be corrected manually!", ex);
                    rfe.setActivity(activity);
                    rfe.setProcessContext(processContext);
                    rfe.setStateItems(stateConfiguration);
                    throw rfe;
                }
            }
        }
    }

    protected boolean shouldExecute(Activity<? extends ProcessContext<CheckoutSeed>> activity, ProcessContext<CheckoutSeed> processContext, Map<String, Object> stateConfiguration) {
        return stateConfiguration != null && (
                stateConfiguration.get(ROLLBACK_BLC_INVENTORY_DECREMENTED) != null ||
                stateConfiguration.get(ROLLBACK_BLC_INVENTORY_INCREMENTED) != null ||
                stateConfiguration.get(EXTENDED_ROLLBACK_STATE) != null
             );
    }

}
