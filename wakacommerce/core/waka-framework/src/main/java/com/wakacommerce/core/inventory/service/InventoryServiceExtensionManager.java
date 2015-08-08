  
package com.wakacommerce.core.inventory.service;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;


@Component("blInventoryServiceExtensionManager")
public class InventoryServiceExtensionManager extends ExtensionManager<InventoryServiceExtensionHandler> {

    public InventoryServiceExtensionManager() {
        super(InventoryServiceExtensionHandler.class);
    }

}
