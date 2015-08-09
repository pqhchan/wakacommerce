
package com.wakacommerce.core.order.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.profile.core.domain.Customer;


/**
 *
 * @ hui
 */
public abstract class AbstractOrderServiceExtensionHandler extends AbstractExtensionHandler implements
        OrderServiceExtensionHandler {
    
    public ExtensionResultStatusType attachAdditionalDataToNewNamedCart(Customer customer, Order cart) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    public ExtensionResultStatusType preValidateCartOperation(Order cart, ExtensionResultHolder erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    public ExtensionResultStatusType preValidateUpdateQuantityOperation(Order cart, OrderItemRequestDTO dto, 
            ExtensionResultHolder erh) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    
    public ExtensionResultStatusType attachAdditionalDataToOrder(Order order, boolean priceOrder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
