
package com.wakacommerce.core.order.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.service.call.OrderItemRequestDTO;
import com.wakacommerce.profile.core.domain.Customer;


/**
 *Andre Azzolini (apazzolini), bpolster
 */
public interface OrderServiceExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType attachAdditionalDataToNewNamedCart(Customer customer, Order cart);

    public ExtensionResultStatusType preValidateCartOperation(Order cart, ExtensionResultHolder erh);

    public ExtensionResultStatusType preValidateUpdateQuantityOperation(Order cart, OrderItemRequestDTO dto, 
            ExtensionResultHolder erh);
    
    /**
     * Can be used to attach or update fields must prior to saving an order.
     * @return
     */
    public ExtensionResultStatusType attachAdditionalDataToOrder(Order order, boolean priceOrder);
}
