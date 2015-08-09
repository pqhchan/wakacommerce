
package com.wakacommerce.core.order.dao;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blOrderDaoExtensionManager")
public class OrderDaoExtensionManager extends ExtensionManager<OrderDaoExtensionHandler> {

    public OrderDaoExtensionManager() {
        super(OrderDaoExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
