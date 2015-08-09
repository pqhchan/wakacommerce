
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blConfirmationControllerExtensionManager")
public class ConfirmationControllerExtensionManager extends ExtensionManager<ConfirmationControllerExtensionHandler> {

    public ConfirmationControllerExtensionManager() {
        super(ConfirmationControllerExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
