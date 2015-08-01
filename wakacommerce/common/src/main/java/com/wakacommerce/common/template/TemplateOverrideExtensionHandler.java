
package com.wakacommerce.common.template;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 * Certain objects may have templates that resolve differently based on Broadleaf modules. This extension handler
 * provides the abilities for modules to provide that functionality.
 * 
 * 
 */
public interface TemplateOverrideExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType getOverrideTemplate(ExtensionResultHolder<String> erh, Object object);


}
