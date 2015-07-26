
package com.wakacommerce.common.template;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *Andre Azzolini (apazzolini)
 */
@Service("blTemplateOverrideExtensionManager")
public class TemplateOverrideExtensionManager extends ExtensionManager<TemplateOverrideExtensionHandler> {
    
    public TemplateOverrideExtensionManager() {
        super(TemplateOverrideExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return true;
    }


}
