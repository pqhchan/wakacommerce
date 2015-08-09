
package com.wakacommerce.openadmin.web.controller;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.openadmin.web.form.entity.DefaultMainActions;


/**
 *
 * @ hui
 */
@Deprecated
@Component("blMainEntityActionsExtensionManager")
public class MainEntityActionsExtensionManager extends ExtensionManager<MainEntityActionsExtensionHandler> {

    public MainEntityActionsExtensionManager() {
        super(MainEntityActionsExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
