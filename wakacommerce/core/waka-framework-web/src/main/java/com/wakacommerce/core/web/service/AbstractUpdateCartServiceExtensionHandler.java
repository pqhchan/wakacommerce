
package com.wakacommerce.core.web.service;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;


/**
 *
 * @ hui
 */
public abstract class AbstractUpdateCartServiceExtensionHandler extends AbstractExtensionHandler
        implements UpdateCartServiceExtensionHandler {

    public ExtensionResultStatusType updateAndValidateCart(Order cart, ExtensionResultHolder resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
