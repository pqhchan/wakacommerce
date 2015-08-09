
package com.wakacommerce.common.copy;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blMultiTenantCopierExtensionManager")
public class MultiTenantCopierExtensionManager extends ExtensionManager<MultiTenantCopierExtensionHandler> {
    
    public MultiTenantCopierExtensionManager() {
        super(MultiTenantCopierExtensionHandler.class);
    }

    public boolean continueOnHandled() {
        return true;
    }

}
