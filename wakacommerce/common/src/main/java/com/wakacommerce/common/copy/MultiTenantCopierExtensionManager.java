
package com.wakacommerce.common.copy;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blMultiTenantCopierExtensionManager")
public class MultiTenantCopierExtensionManager extends ExtensionManager<MultiTenantCopierExtensionHandler> {
    
    public MultiTenantCopierExtensionManager() {
        super(MultiTenantCopierExtensionHandler.class);
    }

    /**
     * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
     */
    public boolean continueOnHandled() {
        return true;
    }

}
