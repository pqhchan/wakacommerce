
package com.wakacommerce.core.inventory.service;

import java.util.Collection;
import java.util.Map;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.checkout.service.workflow.DecrementInventoryActivity;
import com.wakacommerce.core.checkout.service.workflow.DecrementInventoryRollbackHandler;
import com.wakacommerce.core.order.service.workflow.CheckAvailabilityActivity;

/**
 *
 * @ hui
 */
public interface ContextualInventoryService extends InventoryService {

    public static String ORDER_KEY = "ORDER";

    public static String ROLLBACK_STATE_KEY = "ROLLBACK_STATE";

    public Integer retrieveQuantityAvailable(Sku sku, Map<String, Object> context);

    public Map<Sku, Integer> retrieveQuantitiesAvailable(Collection<Sku> skus, Map<String, Object> context);

    public boolean isAvailable(Sku sku, int quantity, Map<String, Object> context);

    public void decrementInventory(Sku sku, int quantity, Map<String, Object> context) throws InventoryUnavailableException;

    public void decrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context) throws InventoryUnavailableException;

    public void incrementInventory(Sku sku, int quantity, Map<String, Object> context);

    public void incrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context);

}
