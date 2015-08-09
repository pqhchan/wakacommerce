package com.wakacommerce.cms.structure.domain;

import javax.annotation.Nonnull;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.QuantityBasedRule;

/**
 *
 * @ hui
 */
public interface StructuredContentItemCriteria extends QuantityBasedRule, MultiTenantCloneable<StructuredContentItemCriteria> {

    @Nonnull
    public StructuredContent getStructuredContent();

    public void setStructuredContent(@Nonnull StructuredContent structuredContent);

    @Nonnull
    public StructuredContentItemCriteria cloneEntity();
    
}
