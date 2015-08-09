
package com.wakacommerce.common.web.resource;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

@Service("blResourceRequestExtensionManager")
public class ResourceRequestExtensionManager extends ExtensionManager<ResourceRequestExtensionHandler>{

    public ResourceRequestExtensionManager() {
        super(ResourceRequestExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return false;
    }

}
