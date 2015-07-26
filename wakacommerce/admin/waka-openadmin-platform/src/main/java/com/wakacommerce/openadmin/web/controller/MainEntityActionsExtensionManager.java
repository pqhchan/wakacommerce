
package com.wakacommerce.openadmin.web.controller;

import org.springframework.stereotype.Component;

import com.wakacommerce.common.extension.ExtensionManager;
import com.wakacommerce.openadmin.web.form.entity.DefaultMainActions;


/**
 * Deprecated - Use {@link com.wakacommerce.openadmin.web.controller.AdminAbstractControllerExtensionManager}
 *
 * Extension manager to modify the actions that are added by default when viewing a ceiling entity for a particular
 * section (for instance, a list of Products in the 'Product' section). Assuming that the user has proper permissions,
 * the mainActions list would have {@link DefaultMainActions#ADD}
 *
 *Phillip Verheyden (phillipuniverse)
 * @see 
 */
@Deprecated
@Component("blMainEntityActionsExtensionManager")
public class MainEntityActionsExtensionManager extends ExtensionManager<MainEntityActionsExtensionHandler> {

    /**
     * @param _clazz
     */
    public MainEntityActionsExtensionManager() {
        super(MainEntityActionsExtensionHandler.class);
    }

    @Override
    public boolean continueOnHandled() {
        return true;
    }

}
