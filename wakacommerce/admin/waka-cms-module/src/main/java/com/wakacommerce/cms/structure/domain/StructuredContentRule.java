package com.wakacommerce.cms.structure.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.SimpleRule;

/**
 *
 * @ hui
 */
public interface StructuredContentRule extends SimpleRule, MultiTenantCloneable<StructuredContentRule> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    public StructuredContentRule cloneEntity();

}
