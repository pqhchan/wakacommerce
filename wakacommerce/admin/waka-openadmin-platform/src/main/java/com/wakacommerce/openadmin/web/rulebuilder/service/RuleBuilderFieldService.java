package com.wakacommerce.openadmin.web.rulebuilder.service;

import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldDTO;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldData;
import com.wakacommerce.openadmin.web.rulebuilder.dto.FieldWrapper;

import java.util.List;

public interface RuleBuilderFieldService extends Cloneable {

    public String getName();

    public FieldWrapper buildFields();

    public FieldDTO getField(String fieldName);

    public SupportedFieldType getSupportedFieldType(String fieldName);

    public SupportedFieldType getSecondaryFieldType(String fieldName);

    public List<FieldData> getFields();

    public void setFields(List<FieldData> fields);

    public RuleBuilderFieldService clone() throws CloneNotSupportedException;

    public void setRuleBuilderFieldServiceExtensionManager(RuleBuilderFieldServiceExtensionManager extensionManager);
}
