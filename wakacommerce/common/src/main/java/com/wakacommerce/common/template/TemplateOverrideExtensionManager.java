
package com.wakacommerce.common.template;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Service("blTemplateOverrideExtensionManager")
public class TemplateOverrideExtensionManager extends ExtensionManager<TemplateOverrideExtensionHandler> {
    
    public TemplateOverrideExtensionManager() {
        super(TemplateOverrideExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }


}
