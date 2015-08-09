  
package com.wakacommerce.openadmin.web.controller;

import org.springframework.ui.Model;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.server.security.domain.AdminSection;
import com.wakacommerce.openadmin.web.controller.entity.AdminBasicEntityController;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.form.entity.EntityFormAction;

import java.util.List;


/**
 *
 * @ hui
 */
public interface AdminAbstractControllerExtensionHandler extends ExtensionHandler {
    
    public static final String NEW_CLASS_NAME = "newClassName";

    public ExtensionResultStatusType addAdditionalMainActions(String sectionClassName, List<EntityFormAction> actions);

    public ExtensionResultStatusType modifyMainActions(ClassMetadata cmd, List<EntityFormAction> mainActions);

    public ExtensionResultStatusType setAdditionalModelAttributes(Model model, String sectionKey);

    public ExtensionResultStatusType overrideClassNameForSection(ExtensionResultHolder erh, String sectionKey, 
            AdminSection section);

    public ExtensionResultStatusType modifyDynamicForm(EntityForm form, String parentEntityId);
}
