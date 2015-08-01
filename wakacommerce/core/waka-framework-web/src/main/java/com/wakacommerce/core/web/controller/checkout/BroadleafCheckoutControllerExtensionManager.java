
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Extension manager for the checkout controller
 *
 *  
 */
@Service("blCheckoutControllerExtensionManager")
public class BroadleafCheckoutControllerExtensionManager extends ExtensionManager<BroadleafCheckoutControllerExtensionHandler> {

    public BroadleafCheckoutControllerExtensionManager() {
        super(BroadleafCheckoutControllerExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
