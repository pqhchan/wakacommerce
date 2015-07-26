/*
 * #%L
 * BroadleafCommerce Admin Module
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
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
package com.wakacommerce.admin.server.service.persistence.validation;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.service.type.OfferType;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.server.service.persistence.validation.PropertyValidationResult;
import com.wakacommerce.openadmin.server.service.persistence.validation.PropertyValidator;
import com.wakacommerce.openadmin.server.service.persistence.validation.RequiredPropertyValidator;

import java.io.Serializable;
import java.util.Map;


/**
 * Validator that ensures that an offer of type {@link OfferType#ORDER_ITEM} has at least one rule for the target criteria
 *
 *Phillip Verheyden (phillipuniverse)
 */
@Component("blTargetItemRulesValidator")
public class TargetItemRulesValidator implements PropertyValidator {

    @Override
    public PropertyValidationResult validate(Entity entity, Serializable instance, Map<String, FieldMetadata> entityFieldMetadata, Map<String, String> validationConfiguration, BasicFieldMetadata propertyMetadata, String propertyName, String value) {
        Offer offer = (Offer)instance;
        if (OfferType.ORDER_ITEM.equals(offer.getType())) {
            return new PropertyValidationResult(CollectionUtils.isNotEmpty(offer.getTargetItemCriteriaXref()),
                    RequiredPropertyValidator.ERROR_MESSAGE);
        } else {
            return new PropertyValidationResult(true);
        }
    }
}
