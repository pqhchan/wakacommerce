/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2014 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.wakacommerce.openadmin.web.controller;

import org.springframework.ui.Model;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.ClassMetadata;
import com.wakacommerce.openadmin.server.security.domain.AdminSection;
import com.wakacommerce.openadmin.web.form.entity.EntityForm;
import com.wakacommerce.openadmin.web.form.entity.EntityFormAction;

import java.util.List;


/**
 * Abstract implementatino of {@link AdminAbstractControllerExtensionHandler}.
 * 
 * Individual implementations of this extension handler should subclass this class as it will allow them to 
 * only override the methods that they need for their particular scenarios.
 * 
 *Andre Azzolini (apazzolini)
 */
public class AbstractAdminAbstractControllerExtensionHandler extends AbstractExtensionHandler implements AdminAbstractControllerExtensionHandler {

    @Override
    public ExtensionResultStatusType addAdditionalMainActions(String sectionClassName, List<EntityFormAction> actions) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType modifyMainActions(ClassMetadata cmd, List<EntityFormAction> mainActions) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType setAdditionalModelAttributes(Model model, String sectionKey) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
    
    @Override
    public ExtensionResultStatusType overrideClassNameForSection(ExtensionResultHolder erh, String sectionKey, 
            AdminSection section) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType modifyDynamicForm(EntityForm form, String parentEntityId) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
