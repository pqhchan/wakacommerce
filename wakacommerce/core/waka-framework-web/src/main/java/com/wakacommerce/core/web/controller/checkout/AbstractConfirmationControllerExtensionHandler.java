
package com.wakacommerce.core.web.controller.checkout;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.core.order.domain.Order;


/**
 *
 * @ hui
 */
public class AbstractConfirmationControllerExtensionHandler extends AbstractExtensionHandler 
        implements ConfirmationControllerExtensionHandler {

    @Override
    public ExtensionResultStatusType processAdditionalConfirmationActions(Order order) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    

}
