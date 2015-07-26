
package com.wakacommerce.core.order.dao;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *Andre Azzolini (apazzolini), bpolster
 */
@Service("blOrderDaoExtensionManager")
public class OrderDaoExtensionManager extends ExtensionManager<OrderDaoExtensionHandler> {

    public OrderDaoExtensionManager() {
        super(OrderDaoExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
