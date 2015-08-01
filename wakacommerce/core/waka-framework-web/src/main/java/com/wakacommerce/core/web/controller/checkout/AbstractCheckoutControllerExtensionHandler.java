/*
 * #%L
 * BroadleafCommerce Framework Web
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
package com.wakacommerce.core.web.controller.checkout;

import org.springframework.ui.Model;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *  
 */
public abstract class AbstractCheckoutControllerExtensionHandler extends AbstractExtensionHandler
        implements BroadleafCheckoutControllerExtensionHandler {

    @Override
    public ExtensionResultStatusType addAdditionalModelVariables(Model model) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType performAdditionalShippingAction() {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
