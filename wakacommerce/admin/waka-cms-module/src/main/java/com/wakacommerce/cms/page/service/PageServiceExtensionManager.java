
package com.wakacommerce.cms.page.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 * ,  
 */
@Service("blPageServiceExtensionManager")
public class PageServiceExtensionManager extends ExtensionManager<PageServiceExtensionHandler> {

    public PageServiceExtensionManager() {
        super(PageServiceExtensionHandler.class);
    }

    /**
     * By default, this manager will allow other handlers to process the method when a handler returns
     * HANDLED.
     */
    @Override
    public boolean continueOnHandled() {
        return false;
    }
}
