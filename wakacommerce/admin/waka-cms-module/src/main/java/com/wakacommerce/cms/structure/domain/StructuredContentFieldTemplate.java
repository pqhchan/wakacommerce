package com.wakacommerce.cms.structure.domain;

import com.wakacommerce.cms.field.domain.FieldGroup;
import com.wakacommerce.common.copy.MultiTenantCloneable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 * A structured content field template holds the structure for a
 * structured content.
 *
 * For example, an 'Ad' template might describe the fields 'Ad URL' and
 * 'Target URL'.   The 'Ad' template might be used in multiple StructuredContentType
 * instances such as 'Home Page Banner Ad' or 'Cart Bottom Ad', etc.
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
