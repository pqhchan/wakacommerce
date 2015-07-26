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
package com.wakacommerce.admin.web.rulebuilder.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.presentation.RuleIdentifier;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldData;
import com.wakacommerce.openadmin.web.rulebuilder.service.AbstractRuleBuilderFieldService;

/**
 * An implementation of a RuleBuilderFieldService
 * that constructs metadata necessary
 * to build the supported fields for a Product entity
 *
 *Andre Azzolini (apazzolini)
 */
@Service("blProductFieldService")
public class ProductFieldServiceImpl extends AbstractRuleBuilderFieldService {

    @Override
    public void init() {
        fields.add(new FieldData.Builder()
                .label("rule_productUrl")
                .name("url")
                .operators("blcOperators_Text")
                .options("[]")
                .type(SupportedFieldType.STRING)
                .build());
        fields.add(new FieldData.Builder()
                .label("rule_productManufacturer")
                .name("manufacturer")
                .operators("blcOperators_Text")
                .options("[]")
                .type(SupportedFieldType.STRING)
                .build());
        fields.add(new FieldData.Builder()
                .label("rule_productSkuName")
                .name("defaultSku.name")
                .operators("blcOperators_Text")
                .options("[]")
                .type(SupportedFieldType.STRING)
                .build());
        fields.add(new FieldData.Builder()
                .label("rule_productSkuLongDescription")
                .name("defaultSku.longDescription")
                .operators("blcOperators_Text")
                .options("[]")
                .type(SupportedFieldType.STRING)
                .build());
    }

    @Override
    public String getName() {
        return RuleIdentifier.PRODUCT;
    }

    @Override
    public String getDtoClassName() {
        return "com.wakacommerce.core.catalog.domain.ProductImpl";
    }
}
