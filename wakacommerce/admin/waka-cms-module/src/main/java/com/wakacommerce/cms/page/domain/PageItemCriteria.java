 
package com.wakacommerce.cms.page.domain;

import javax.annotation.Nonnull;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.QuantityBasedRule;

/**
 *
 * @ hui
 */
public interface PageItemCriteria extends QuantityBasedRule,MultiTenantCloneable<PageItemCriteria> {

    @Nonnull
    public Page getPage();

    public void setPage(@Nonnull Page page);

    @Nonnull
    public PageItemCriteria cloneEntity();
    
}
