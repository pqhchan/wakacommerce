
package com.wakacommerce.core.web.order.security.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blAuthSuccessHandlerExtensionManager")
public class AuthSuccessHandlerExtensionManager extends ExtensionManager<AuthSuccessHandlerExtensionHandler> {

    public AuthSuccessHandlerExtensionManager() {
        super(AuthSuccessHandlerExtensionHandler.class);
    }

    public boolean continueOnHandled() {
        return true;
    }

}
