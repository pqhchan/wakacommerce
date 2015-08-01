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
package com.wakacommerce.openadmin.server.service.persistence.module.extension;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.service.persistence.module.BasicPersistenceModule;

import java.io.Serializable;
import java.util.Map;

/**
 * Convenience implementation of interface so that subclasses do not have to implement uninteresting methods
 *
 * @see com.wakacommerce.openadmin.server.service.persistence.module.extension.BasicPersistenceModuleExtensionHandler
 * 
 */
public class AbstractBasicPersistenceModuleExtensionHandler extends AbstractExtensionHandler implements BasicPersistenceModuleExtensionHandler {

    @Override
    public ExtensionResultStatusType rebalanceForUpdate(BasicPersistenceModule basicPersistenceModule,
                                                        PersistencePackage persistencePackage, Serializable instance,
                                                        Map<String, FieldMetadata> mergedProperties,
                                                        Object primaryKey, ExtensionResultHolder<Serializable> resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType rebalanceForAdd(BasicPersistenceModule basicPersistenceModule,
                                                     PersistencePackage persistencePackage, Serializable instance,
                                                     Map<String, FieldMetadata> mergedProperties,
                                                     ExtensionResultHolder<Serializable> resultHolder) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }
}
