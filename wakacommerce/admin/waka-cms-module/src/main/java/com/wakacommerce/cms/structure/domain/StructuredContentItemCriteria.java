 
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
public interface StructuredContentItemCriteria extends QuantityBasedRule,MultiTenantCloneable<StructuredContentItemCriteria> {

    /**
     * Returns the parent <code>StructuredContent</code> item to which this
     * field belongs.
     *
     * @return
     */
    @Nonnull
    public StructuredContent getStructuredContent();

    /**
     * Sets the parent <code>StructuredContent</code> item.
     * @param structuredContent
     */
    public void setStructuredContent(@Nonnull StructuredContent structuredContent);

    /**
     * Builds a copy of this item.   Used by the content management system when an
     * item is edited.
     *
     * @return a copy of this item
     */
    @Nonnull
    public StructuredContentItemCriteria cloneEntity();
    
}
