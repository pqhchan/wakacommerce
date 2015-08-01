
package com.wakacommerce.common.template;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


/**
 * 
 */
public abstract class AbstractTemplateOverrideExtensionHandler extends AbstractExtensionHandler implements TemplateOverrideExtensionHandler {
    
    @Override
    public ExtensionResultStatusType getOverrideTemplate(ExtensionResultHolder<String> erh, Object object) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
