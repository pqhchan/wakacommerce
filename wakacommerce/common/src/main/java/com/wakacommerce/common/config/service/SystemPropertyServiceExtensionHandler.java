
package com.wakacommerce.common.config.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


/**
 *
 * @ hui
 */
public interface SystemPropertyServiceExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType resolveProperty(String propertyName, ExtensionResultHolder resultHolder);
    
}
