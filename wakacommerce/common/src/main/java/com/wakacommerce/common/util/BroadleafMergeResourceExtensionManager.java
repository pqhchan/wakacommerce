
package com.wakacommerce.common.util;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

@Component("blBroadleafMergeResourceExtensionManager")
public class BroadleafMergeResourceExtensionManager extends ExtensionManager<BroadleafMergeResourceExtensionHandler>{

    public  BroadleafMergeResourceExtensionManager() {
        super(BroadleafMergeResourceExtensionHandler.class);
    }

}