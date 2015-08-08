  
package com.wakacommerce.core.inventory.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.checkout.service.workflow.DecrementInventoryActivity;
import com.wakacommerce.core.order.service.workflow.CheckAvailabilityActivity;

import java.util.Collection;
import java.util.Map;
import java.util.Set;



/**
 * Marker interface to dictate the overridden methods within {@link ContextualInventoryService}. Usually, implementers
 * will want to only override the {@link ContextualInventoryService} methods rather than all of the methods included
 * in {@link InventoryService} and so you will extend from {@link AbstractInventoryServiceExtensionHandler}.
 * 
 *     
 * @see {@link ContextualInventoryService}
 * @see {@link AbstractInventoryServiceExtensionHandler}
 */
public interface InventoryServiceExtensionHandler extends ExtensionHandler {

    /**
     * Usually invoked within the {@link CheckAvailabilityActivity} to retrieve the quantity that is available for the given
     * <b>skus</b>.
     * 
     * @param context can be null. If not null, this should at least contain the {@link ContextualInventoryService#ORDER_KEY}
     * @see {@link ContextualInventoryService#retrieveQuantitiesAvailable(Set, Map)}
     */
    public ExtensionResultStatusType retrieveQuantitiesAvailable(Collection<Sku> skus, Map<String, Object> context, ExtensionResultHolder<Map<Sku, Integer>> result);
    
    /**
     * Usually invoked within the {@link DecrementInventoryActivity} to decrement inventory for the {@link Sku}s that are in
     * <b>skuQuantities</b>
     * 
     * @param context can be null. If not null, this should at least contain the {@link ContextualInventoryService#ORDER_KEY} and/or the
     * {@link ContextualInventoryService#ROLLBACK_STATE_KEY}
     * @see {@link ContextualInventoryService#decrementInventory(Map, Map)}
     */
    public ExtensionResultStatusType decrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context) throws InventoryUnavailableException;

    /**
     * @param context can be null. If not null, this should at least contain the {@link ContextualInventoryService#ROLLBACK_STATE_KEY}
     * @see {@link ContextualInventoryService#incrementInventory(Map, Map)}
     */
    public ExtensionResultStatusType incrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context);
    
}
