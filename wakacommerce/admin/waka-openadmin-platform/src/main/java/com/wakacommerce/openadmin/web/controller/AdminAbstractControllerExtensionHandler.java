  
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
 * Extension handler for methods present in {@link AdminAbstractController}.
 * 
 * 
 */
public interface AdminAbstractControllerExtensionHandler extends ExtensionHandler {
    
    public static final String NEW_CLASS_NAME = "newClassName";

    /**
     * Invoked every time {@link AdminBasicEntityController#viewEntityList()} is invoked to allow additional
     * main form actions to be contributed.
     * 
     * @param model
     * @param sectionKey
     * @return
     */
    public ExtensionResultStatusType addAdditionalMainActions(String sectionClassName, List<EntityFormAction> actions);

    /**
     * Extension point to override the actions that are added by default when viewing a ceiling entity for a particular
     * section (for instance, a list of Products in the 'Product' section). Assuming that the user has proper permissions,
     * the mainActions list would have {@link com.wakacommerce.openadmin.web.form.entity.DefaultMainActions#ADD}
     *
     * @param cmd the metadata for the ceiling entity that is being displayed
     * @param mainActions the actions that are added to the main form by default. Use this list to add more actions
     */
    public ExtensionResultStatusType modifyMainActions(ClassMetadata cmd, List<EntityFormAction> mainActions);

    /**
     * Invoked every time {@link AdminAbstractController#setModelAttributes(Model, String)} is invoked.
     * 
     * @param model
     * @param sectionKey
     * @return the extension result status
     */
    public ExtensionResultStatusType setAdditionalModelAttributes(Model model, String sectionKey);

    /**
     * Invoked whenever {@link AdminAbstractController#getClassNameForSection(String)} is invoked. If an extension
     * handler sets the {@link #NEW_CLASS_NAME} variable in the ExtensionResultHolder, the overriden value will be used.
     * 
     * @param erh
     * @param sectionKey
     * @param section
     * @return
     */
    public ExtensionResultStatusType overrideClassNameForSection(ExtensionResultHolder erh, String sectionKey, 
            AdminSection section);

    /**
     * Invoked whenever {@link AdminAbstractController#getBlankDynamicFieldTemplateForm} or 
     * {@link AdminAbstractController#getDynamicFieldTemplateForm} is invoked. This method provides the ability to modify
     * the dynamic form that is created as a result of those two methods.
     * 
     * @param form
     * @param parentEntityId
     * @return
     */
    public ExtensionResultStatusType modifyDynamicForm(EntityForm form, String parentEntityId);
}
