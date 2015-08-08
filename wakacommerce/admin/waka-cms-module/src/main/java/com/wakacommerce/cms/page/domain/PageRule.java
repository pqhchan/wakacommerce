package com.wakacommerce.cms.page.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.SimpleRule;

/**
 * Implementations hold the values for a rule used to determine if a <code>Page</code>
 * should be displayed.
 * <br>
 * The rule is represented as a valid MVEL string.    The Content Management System by default
 * is able to process rules based on the current customer, product,
 * {@link com.wakacommerce.common.TimeDTO time}, or {@link com.wakacommerce.common.RequestDTO request}
 *
 * @see com.wakacommerce.cms.web.structure.DisplayContentTag
 * @see com.wakacommerce.cms.structure.service.PageServiceImpl#evaluateAndPriortizePages(java.util.List, int, java.util.Map)
 * 
 *
 */
public interface PageRule extends SimpleRule, MultiTenantCloneable<PageRule> {

    /**
     * Gets the primary key.
     *
     * @return the primary key
     */
    @Nullable
    public Long getId();

    /**
     * Sets the primary key.
     *
     * @param id the new primary key
     */
    public void setId(@Nullable Long id);

    /**
     * Builds a copy of this content rule.   Used by the content management system when an
     * item is edited.
     *
     * @return a copy of this rule
     */
    @Nonnull
    public PageRule cloneEntity();

}
