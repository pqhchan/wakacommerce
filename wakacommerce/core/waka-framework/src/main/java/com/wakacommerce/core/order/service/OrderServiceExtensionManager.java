
package com.wakacommerce.core.order.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blOrderServiceExtensionManager")
public class OrderServiceExtensionManager extends ExtensionManager<OrderServiceExtensionHandler> {

    public OrderServiceExtensionManager() {
        super(OrderServiceExtensionHandler.class);
    }

    public boolean continueOnHandled() {
        return true;
    }
}
