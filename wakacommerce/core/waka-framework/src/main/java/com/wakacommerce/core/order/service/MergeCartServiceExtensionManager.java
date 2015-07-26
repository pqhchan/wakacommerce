
package com.wakacommerce.core.order.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * Extension manager for merge cart.
 * 
 *Andre Azzolini (apazzolini)
 */
@Service("blMergeCartServiceExtensionManager")
public class MergeCartServiceExtensionManager extends ExtensionManager<MergeCartServiceExtensionHandler> {

    public MergeCartServiceExtensionManager() {
        super(MergeCartServiceExtensionHandler.class);
    }

    /**
     * By default,this extension manager will continue on handled allowing multiple handlers to interact with the order.
     */
    public boolean continueOnHandled() {
        return true;
    }

}
