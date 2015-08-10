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
 *
 * @ hui
 */
public interface InventoryServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType retrieveQuantitiesAvailable(Collection<Sku> skus, Map<String, Object> context, ExtensionResultHolder<Map<Sku, Integer>> result);

    public ExtensionResultStatusType decrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context) throws InventoryUnavailableException;

    public ExtensionResultStatusType incrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context);
    
}
