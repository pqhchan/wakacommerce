
package com.wakacommerce.common.template;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public interface TemplateOverrideExtensionHandler extends ExtensionHandler {
    
    public ExtensionResultStatusType getOverrideTemplate(ExtensionResultHolder<String> erh, Object object);


}
