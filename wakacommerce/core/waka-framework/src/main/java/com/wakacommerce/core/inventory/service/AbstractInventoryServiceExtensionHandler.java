  
package com.wakacommerce.core.inventory.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.catalog.domain.Sku;

import java.util.Collection;
import java.util.Map;


public abstract class AbstractInventoryServiceExtensionHandler extends AbstractExtensionHandler implements InventoryServiceExtensionHandler {

    @Override
    public ExtensionResultStatusType retrieveQuantitiesAvailable(Collection<Sku> skus, Map<String, Object> context, ExtensionResultHolder<Map<Sku, Integer>> result) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType decrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context) throws InventoryUnavailableException {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType incrementInventory(Map<Sku, Integer> skuQuantities, Map<String, Object> context) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    
}
