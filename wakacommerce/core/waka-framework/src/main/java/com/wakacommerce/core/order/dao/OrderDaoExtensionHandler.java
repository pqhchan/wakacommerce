
package com.wakacommerce.core.order.dao;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;


/**
 * ,  
 */
public interface OrderDaoExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType attachAdditionalDataToNewCart(Customer customer, Order cart);

    public ExtensionResultStatusType processPostSaveNewCart(Customer customer, Order cart);
    
    public ExtensionResultStatusType applyAdditionalOrderLookupFilter(Customer customer, String name, List<Order> orders);

}
