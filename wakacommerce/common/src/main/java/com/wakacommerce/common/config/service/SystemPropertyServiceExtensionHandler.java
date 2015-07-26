
package com.wakacommerce.common.config.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


/**
 *bpolster
 */
public interface SystemPropertyServiceExtensionHandler extends ExtensionHandler {
    
    /**
     * Provides an opportunity for modules to resolve a system property.
     * 
     * @param propertyName
     * @param resultHolder
     * @return
     */
    public ExtensionResultStatusType resolveProperty(String propertyName, ExtensionResultHolder resultHolder);
    
}
