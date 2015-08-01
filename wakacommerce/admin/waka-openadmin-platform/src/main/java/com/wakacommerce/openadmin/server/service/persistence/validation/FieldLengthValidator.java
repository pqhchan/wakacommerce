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
package com.wakacommerce.openadmin.server.service.persistence.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;

import java.io.Serializable;
import java.util.Map;


/**
 * Ensures that field values submitted in the admin are less than or equal to the length specified in the metadata
 * 
 *     
 */
@Component("blFieldLengthValidator")
public class FieldLengthValidator implements GlobalPropertyValidator {

    @Override
    public PropertyValidationResult validate(Entity entity,
            Serializable instance,
            Map<String, FieldMetadata> entityFieldMetadata,
            BasicFieldMetadata propertyMetadata,
            String propertyName,
            String value) {
        boolean valid = true;
        String errorMessage = "";
        if (propertyMetadata.getLength() != null) {
            valid = StringUtils.length(value) <= propertyMetadata.getLength();
        }
        
        if (!valid) {
            WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
            MessageSource messages = context.getMessageSource();
            errorMessage = messages.getMessage("fieldLengthValidationFailure",
                    new Object[] {propertyMetadata.getLength(), StringUtils.length(value) },
                    context.getJavaLocale());
        }
        
        return new PropertyValidationResult(valid, errorMessage);
    }

}
