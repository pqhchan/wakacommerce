
package com.wakacommerce.cms.page.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;


/**
 *
 * @ hui
 */
@Service("blPageServiceExtensionManager")
public class PageServiceExtensionManager extends ExtensionManager<PageServiceExtensionHandler> {

    public PageServiceExtensionManager() {
        super(PageServiceExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return false;
    }
}
