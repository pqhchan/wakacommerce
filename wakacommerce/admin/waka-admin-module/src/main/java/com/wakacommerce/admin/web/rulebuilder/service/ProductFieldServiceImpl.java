package com.wakacommerce.admin.web.rulebuilder.service;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.presentation.RuleIdentifier;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldData;
import com.wakacommerce.openadmin.web.rulebuilder.service.AbstractRuleBuilderFieldService;

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
