
package com.wakacommerce.openadmin.web.controller;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.web.form.entity.DefaultMainActions;
import com.wakacommerce.openadmin.web.form.entity.EntityFormAction;

import java.util.List;


/**
 *
 * @ hui
 */
@Deprecated
public interface MainEntityActionsExtensionHandler extends ExtensionHandler {

    public void modifyMainActions(ClassMetadata cmd, List<EntityFormAction> mainActions);
    
}
