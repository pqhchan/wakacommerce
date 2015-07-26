
package com.wakacommerce.core.order.dao;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.profile.core.domain.Customer;

import java.util.List;


/**
 *Andre Azzolini (apazzolini), bpolster
 */
public class AbstractOrderDaoExtensionHandler extends AbstractExtensionHandler implements OrderDaoExtensionHandler {
    
    public ExtensionResultStatusType attachAdditionalDataToNewCart(Customer customer, Order cart) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    public ExtensionResultStatusType processPostSaveNewCart(Customer customer, Order cart) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    
    public ExtensionResultStatusType applyAdditionalOrderLookupFilter(Customer customer, String name, List<Order> orders) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
