  
package com.wakacommerce.openadmin.web.service;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * Allows extension handlers to add a custom error message or error key to the list grid record.
 * 
 *  
 *
 */
@Component("blListGridErrorMessageExtensionManager")
public class ListGridErrorMessageExtensionManager extends ExtensionManager<ListGridErrorMessageExtensionHandler> {

    public ListGridErrorMessageExtensionManager() {
        super(ListGridErrorMessageExtensionHandler.class);
    }

}
