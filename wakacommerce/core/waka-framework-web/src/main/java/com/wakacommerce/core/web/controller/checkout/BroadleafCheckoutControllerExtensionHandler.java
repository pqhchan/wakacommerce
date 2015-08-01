
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.ui.Model;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 * Extension handler for the checkout controller
 *
 *  
 */
public interface BroadleafCheckoutControllerExtensionHandler extends ExtensionHandler {

    /**
     *  Allow other modules to add properties to the checkout controller model
     * 
     * @param model
     * @return
     */
    public ExtensionResultStatusType addAdditionalModelVariables(Model model);

    /**
     * Allow other modules to execute additional logic in shipping methods
     * 
     * @return
     */
    public ExtensionResultStatusType performAdditionalShippingAction();
}

