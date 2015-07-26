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
package com.wakacommerce.common.web.validator;

import org.springframework.validation.Errors;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.web.form.BroadleafFormType;
import com.wakacommerce.profile.core.domain.Address;

/**
 *Elbert Bautista (elbertbautista)
 */
public abstract class AbstractCommonAddressValidatorExtensionHandler extends AbstractExtensionHandler
        implements BroadleafCommonAddressValidatorExtensionHandler {

    @Override
    public ExtensionResultStatusType validate(BroadleafFormType formType, Address address, Errors errors) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}
