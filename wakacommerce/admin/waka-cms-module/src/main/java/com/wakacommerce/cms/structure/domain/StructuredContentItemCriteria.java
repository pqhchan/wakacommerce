package com.wakacommerce.cms.structure.domain;

import javax.annotation.Nonnull;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.QuantityBasedRule;

/**
 * Implementations of this interface contain item rule data that is used for targeting
 * <code>StructuredContent</code> items.
 * <br>
 * <br>
 * For example, a <code>StructuredContent</code> item could be setup to only show to user's
 * who have a particular product in their cart.
 *
 * @see com.wakacommerce.core.order.service.StructuredContentCartRuleProcessor
 * 
 */
public interface StructuredContentItemCriteria extends QuantityBasedRule, MultiTenantCloneable<StructuredContentItemCriteria> {

    @Nonnull
    public StructuredContent getStructuredContent();

    public void setStructuredContent(@Nonnull StructuredContent structuredContent);

    @Nonnull
    public StructuredContentItemCriteria cloneEntity();
    
}
