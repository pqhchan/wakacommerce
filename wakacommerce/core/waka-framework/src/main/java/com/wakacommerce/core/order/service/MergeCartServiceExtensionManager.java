
package com.wakacommerce.core.order.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blMergeCartServiceExtensionManager")
public class MergeCartServiceExtensionManager extends ExtensionManager<MergeCartServiceExtensionHandler> {

    public MergeCartServiceExtensionManager() {
        super(MergeCartServiceExtensionHandler.class);
    }

    public boolean continueOnHandled() {
        return true;
    }

}
