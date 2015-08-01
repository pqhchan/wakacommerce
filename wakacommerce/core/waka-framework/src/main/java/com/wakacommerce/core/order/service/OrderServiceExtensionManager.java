
package com.wakacommerce.core.order.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * ,  
 */
@Service("blOrderServiceExtensionManager")
public class OrderServiceExtensionManager extends ExtensionManager<OrderServiceExtensionHandler> {

    public OrderServiceExtensionManager() {
        super(OrderServiceExtensionHandler.class);
    }

    /**
     * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
     */
    public boolean continueOnHandled() {
        return true;
    }
}
