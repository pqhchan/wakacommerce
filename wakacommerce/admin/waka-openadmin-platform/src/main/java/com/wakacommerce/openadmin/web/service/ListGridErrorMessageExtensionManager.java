  
package com.wakacommerce.openadmin.web.service;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 *
 * @ hui
 */
@Component("blListGridErrorMessageExtensionManager")
public class ListGridErrorMessageExtensionManager extends ExtensionManager<ListGridErrorMessageExtensionHandler> {

    public ListGridErrorMessageExtensionManager() {
        super(ListGridErrorMessageExtensionHandler.class);
    }

}
