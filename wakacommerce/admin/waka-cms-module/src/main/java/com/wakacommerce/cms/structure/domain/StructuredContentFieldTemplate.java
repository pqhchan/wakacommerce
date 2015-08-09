package com.wakacommerce.cms.structure.domain;

import com.wakacommerce.cms.field.domain.FieldGroup;
import com.wakacommerce.common.copy.MultiTenantCloneable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @ hui
 */
public interface StructuredContentFieldTemplate extends Serializable, MultiTenantCloneable<StructuredContentFieldTemplate> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    String getName();

    void setName(@Nonnull String name);

    @Nullable
    List<FieldGroup> getFieldGroups();

    void setFieldGroups(@Nullable List<FieldGroup> fieldGroups);
    
}
