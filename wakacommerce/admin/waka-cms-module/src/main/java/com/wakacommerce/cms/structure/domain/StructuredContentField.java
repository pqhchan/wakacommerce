package com.wakacommerce.cms.structure.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.openadmin.audit.AdminAuditable;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @ hui
 */
public interface StructuredContentField extends Serializable, Cloneable, MultiTenantCloneable<StructuredContentField> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    public String getFieldKey();

    public void setFieldKey(@Nonnull String fieldKey);

    public void setValue(@Nonnull String value);

    @Nonnull
    public String getValue();

    @Nullable
    public AdminAuditable getAuditable();

    public void setAuditable(@Nullable AdminAuditable auditable);

    public StructuredContentField clone();

}
