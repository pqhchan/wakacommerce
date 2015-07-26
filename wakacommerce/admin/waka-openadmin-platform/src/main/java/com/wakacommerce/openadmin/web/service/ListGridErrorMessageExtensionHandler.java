/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2015 Broadleaf Commerce
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
package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.web.form.component.ListGridRecord;

/**
 * An extension handler to allow a custom error key or error message to be added to the ListGridRecord.
 *kellytisdell
 *
 */
public interface ListGridErrorMessageExtensionHandler extends ExtensionHandler {

    /**
     * Allows the extension handler to determine a custom error message or error message key for the entity. 
     * Implementors should determine if they can handle the entity in question. If not, they should return 
     * ExtensionResultStatusType.NOT_HANDLED.
     * 
     * Otherwise, they should either set the error message or the error key on the ListGrid on the entity. If both 
     * are set the error message will win.
     * 
     * Implementors can use the BroadleafRequestContext to try to determine Locale, or get a MessageSource, etc.
     * 
     * @param entity
     * @param lgr
     * @return
     */
    public ExtensionResultStatusType determineErrorMessageForEntity(Entity entity, ListGridRecord lgr);

}
