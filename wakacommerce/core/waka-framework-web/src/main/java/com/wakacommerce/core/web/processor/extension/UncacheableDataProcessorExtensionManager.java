
package com.wakacommerce.core.web.processor.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blUncacheableDataProcessorExtensionManager")
public class UncacheableDataProcessorExtensionManager extends ExtensionManager<UncacheableDataProcessorExtensionHandler> {

    public UncacheableDataProcessorExtensionManager() {
        super(UncacheableDataProcessorExtensionHandler.class);
    }

}
