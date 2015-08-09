
package com.wakacommerce.core.order.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.profile.core.domain.Customer;


/**
 *
 * @ hui
 */
public interface MergeCartServiceExtensionHandler extends ExtensionHandler {
    
    ExtensionResultStatusType setNewCartOwnership(Order cart, Customer customer);

    ExtensionResultStatusType updateMergedOrder(Order cart, Customer customer);
    
}
