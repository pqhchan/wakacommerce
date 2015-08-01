
package com.wakacommerce.core.web.processor.extension;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Provides internal extension points for functionality in the {@link com.wakacommerce.core.web.processor.extension.UncacheableDataProcessor}
 *
 * 
 */
@Service("blUncacheableDataProcessorExtensionManager")
public class UncacheableDataProcessorExtensionManager extends ExtensionManager<UncacheableDataProcessorExtensionHandler> {

    public UncacheableDataProcessorExtensionManager() {
        super(UncacheableDataProcessorExtensionHandler.class);
    }

}
