
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.ui.Model;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface BroadleafCheckoutControllerExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType addAdditionalModelVariables(Model model);

    public ExtensionResultStatusType performAdditionalShippingAction();
}

