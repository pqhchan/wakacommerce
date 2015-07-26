
package com.wakacommerce.core.web.controller.checkout;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;


/**
 *Andre Azzolini (apazzolini)
 */
public interface ConfirmationControllerExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType processAdditionalConfirmationActions(Order order);

}
