

package com.wakacommerce.core.web.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;


/**
 *
 * @ hui
 */
public interface UpdateCartServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType updateAndValidateCart(Order cart, ExtensionResultHolder resultHolder);

}
