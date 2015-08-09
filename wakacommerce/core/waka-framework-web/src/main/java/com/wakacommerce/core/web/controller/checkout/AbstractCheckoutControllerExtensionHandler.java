
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.ui.Model;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public abstract class AbstractCheckoutControllerExtensionHandler extends AbstractExtensionHandler
        implements BroadleafCheckoutControllerExtensionHandler {

    @Override
    public ExtensionResultStatusType addAdditionalModelVariables(Model model) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType performAdditionalShippingAction() {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
