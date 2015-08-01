
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * 
 */
@Service("blConfirmationControllerExtensionManager")
public class ConfirmationControllerExtensionManager extends ExtensionManager<ConfirmationControllerExtensionHandler> {

    public ConfirmationControllerExtensionManager() {
        super(ConfirmationControllerExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }
}
