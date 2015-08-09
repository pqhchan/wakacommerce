
package com.wakacommerce.core.web.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blUpdateCartServiceExtensionManager")
public class UpdateCartServiceExtensionManager extends ExtensionManager<UpdateCartServiceExtensionHandler> {
    
    public UpdateCartServiceExtensionManager() {
        super(UpdateCartServiceExtensionHandler.class);
    }
}
