package com.wakacommerce.cms.page.domain;

import javax.annotation.Nonnull;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.QuantityBasedRule;

/**
 * Implementations of this interface contain item rule data that is used for targeting
 * <code>Page</code>s.
 * <br>
 * <br>
 * For example, a <code>Page</code>  could be setup to only show to user's
 * who have a particular product in their cart.
 *
 * @see com.wakacommerce.core.order.service.PageCartRuleProcessor
 * 
 */
public interface PageItemCriteria extends QuantityBasedRule, MultiTenantCloneable<PageItemCriteria> {

    /**
     * Returns the parent <code>Page</code> to which this
     * field belongs.
     *
     * @return
     */
    @Nonnull
    public Page getPage();

    /**
     * Sets the parent <code>Page</code>.
     * @param page
     */
    public void setPage(@Nonnull Page page);

    /**
     * Builds a copy of this item.   Used by the content management system when an
     * item is edited.
     *
     * @return a copy of this item
     */
    @Nonnull
    public PageItemCriteria cloneEntity();
    
}
