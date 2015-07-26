
package com.wakacommerce.core.web.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *Andre Azzolini (apazzolini), bpolster
 */
@Service("blUpdateCartServiceExtensionManager")
public class UpdateCartServiceExtensionManager extends ExtensionManager<UpdateCartServiceExtensionHandler> {
    
    public UpdateCartServiceExtensionManager() {
        super(UpdateCartServiceExtensionHandler.class);
    }
}
