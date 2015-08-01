package com.wakacommerce.cms.structure.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

import java.io.Serializable;

public interface StructuredContentType extends Serializable, MultiTenantCloneable<StructuredContentType> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    String getName();

    void setName(@Nonnull String name);

    @Nullable
    String getDescription();

    void setDescription(@Nullable String description);

    @Nonnull
    StructuredContentFieldTemplate getStructuredContentFieldTemplate();

    void setStructuredContentFieldTemplate(@Nonnull StructuredContentFieldTemplate scft);
    
}
