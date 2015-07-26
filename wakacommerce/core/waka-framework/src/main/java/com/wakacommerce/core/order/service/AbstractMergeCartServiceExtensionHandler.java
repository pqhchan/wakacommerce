
package com.wakacommerce.core.order.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.profile.core.domain.Customer;

/**
 *Andre Azzolini (apazzolini)
 */
public abstract class AbstractMergeCartServiceExtensionHandler extends AbstractExtensionHandler implements
        MergeCartServiceExtensionHandler {
    
    public ExtensionResultStatusType setNewCartOwnership(Order cart, Customer customer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    
    public ExtensionResultStatusType updateMergedOrder(Order cart, Customer customer) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
