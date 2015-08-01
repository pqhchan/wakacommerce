
package com.wakacommerce.openadmin.web.controller;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.web.form.entity.DefaultMainActions;
import com.wakacommerce.openadmin.web.form.entity.EntityFormAction;

import java.util.List;


/**
 * Deprecated - Use {@link com.wakacommerce.openadmin.web.controller.AdminAbstractControllerExtensionHandler}
 *
 *     
 */
@Deprecated
public interface MainEntityActionsExtensionHandler extends ExtensionHandler {

    /**
     * Extension point to override the actions that are added by default when viewing a ceiling entity for a particular
     * section (for instance, a list of Products in the 'Product' section). Assuming that the user has proper permissions,
     * the mainActions list would have {@link DefaultMainActions#ADD}
     * 
     * @param cmd the metadata for the ceiling entity that is being displayed
     * @param mainActions the actions that are added to the main form by default. Use this list to add more actions
     */
    public void modifyMainActions(ClassMetadata cmd, List<EntityFormAction> mainActions);
    
}
