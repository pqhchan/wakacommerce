 
package com.wakacommerce.cms.page.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.SimpleRule;

/**
 *
 * @ hui
 */
public interface PageRule extends SimpleRule,MultiTenantCloneable<PageRule> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    public PageRule cloneEntity();

}
