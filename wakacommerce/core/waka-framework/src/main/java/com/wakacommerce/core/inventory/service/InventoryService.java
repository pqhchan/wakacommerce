package com.wakacommerce.core.inventory.service;

import java.util.Collection;
import java.util.Map;

import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.inventory.service.type.InventoryType;

/**
 *
 * @ hui
 */
public interface InventoryService {

    public Integer retrieveQuantityAvailable(Sku sku);

    public Map<Sku, Integer> retrieveQuantitiesAvailable(Collection<Sku> skus);

    public boolean isAvailable(Sku sku, int quantity);

    public boolean checkBasicAvailablility(Sku sku);

    public void decrementInventory(Sku sku, int quantity) throws InventoryUnavailableException;

    public void decrementInventory(Map<Sku, Integer> skuQuantities) throws InventoryUnavailableException;

    public void incrementInventory(Sku sku, int quantity);

    public void incrementInventory(Map<Sku, Integer> skuQuantities);

}
