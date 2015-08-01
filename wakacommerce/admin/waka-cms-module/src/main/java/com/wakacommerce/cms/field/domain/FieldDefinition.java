package com.wakacommerce.cms.field.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.enumeration.domain.DataDrivenEnumeration;
import com.wakacommerce.common.presentation.client.SupportedFieldType;

public interface FieldDefinition extends Serializable, MultiTenantCloneable<FieldDefinition> {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public SupportedFieldType getFieldType();

    public void setFieldType(SupportedFieldType fieldType);

    public String getSecurityLevel();

    public void setSecurityLevel(String securityLevel);

    public Boolean getHiddenFlag();

    public void setHiddenFlag(Boolean hiddenFlag);

    public String getValidationRegEx();

    public void setValidationRegEx(String validationRegEx);

    public Integer getMaxLength();

    public void setMaxLength(Integer maxLength);

    public String getColumnWidth();

    public void setColumnWidth(String columnWidth);

    public Boolean getTextAreaFlag();

    public void setTextAreaFlag(Boolean textAreaFlag);
    
    public Boolean getRequiredFlag();

    public void setRequiredFlag(Boolean requiredFlag);

    public DataDrivenEnumeration getDataDrivenEnumeration();
    
    public void setDataDrivenEnumeration(DataDrivenEnumeration dataDrivenEnumeration);

    public Boolean getAllowMultiples();

    public void setAllowMultiples(Boolean allowMultiples);

    public String getFriendlyName();

    public void setFriendlyName(String friendlyName);

    public String getValidationErrorMesageKey();

    public void setValidationErrorMesageKey(String validationErrorMesageKey);

    public FieldGroup getFieldGroup();

    public void setFieldGroup(FieldGroup fieldGroup);

    public int getFieldOrder();

    public void setFieldOrder(int fieldOrder);

    public String getTooltip();

    public void setTooltip(String tooltip);

    public String getHelpText();

    public void setHelpText(String helpText);

    public String getHint();

    public void setHint(String hint);

    public String getAdditionalForeignKeyClass();

    public void setAdditionalForeignKeyClass(String className);

}
