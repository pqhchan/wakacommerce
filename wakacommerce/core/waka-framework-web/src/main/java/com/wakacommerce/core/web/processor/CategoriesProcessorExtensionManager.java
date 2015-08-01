
package com.wakacommerce.core.web.processor;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * 
 */
@Service("blCategoriesProcessorExtensionManager")
public class CategoriesProcessorExtensionManager extends ExtensionManager<CategoriesProcessorExtensionHandler> {

    public CategoriesProcessorExtensionManager() {
        super(CategoriesProcessorExtensionHandler.class);
    }

}
