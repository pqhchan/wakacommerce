/*
 * #%L
 * BroadleafCommerce Framework
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
package com.wakacommerce.admin.server.service.handler;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.extension.ExtensionManager;

/**
 * For internal usage. Allows extension of entity API calls without subclassing the entity.
 *
 *Jeff Fischer
 */
@Service("blSkuCustomPersistenceHandlerExtensionManager")
public class SkuCustomPersistenceHandlerExtensionManager extends ExtensionManager<SkuCustomPersistenceHandlerExtensionHandler> {

    public SkuCustomPersistenceHandlerExtensionManager() {
        super(SkuCustomPersistenceHandlerExtensionHandler.class);
    }

}