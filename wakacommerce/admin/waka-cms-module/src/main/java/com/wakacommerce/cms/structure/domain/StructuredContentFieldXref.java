package com.wakacommerce.cms.structure.domain;


import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface StructuredContentFieldXref extends MultiTenantCloneable<StructuredContentFieldXref> {

    public void setId(Long id);

    public Long getId();

    public void setStructuredContent(StructuredContent sc);

    public StructuredContent getStructuredContent();

    public void setStrucuturedContentField(StructuredContentField scField);

    public StructuredContentField getStructuredContentField();

    public void setKey(String key);

    public String getKey();

}
