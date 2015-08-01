package com.wakacommerce.cms.structure.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.rule.SimpleRule;

/**
 * 用来判断StructuredContent是否显示的规则
 * <br>
 * CMS系统默认情况下，能够处理基于当前customer, product, 
 * {@link com.wakacommerce.common.TimeDTO time} 或 {@link com.wakacommerce.common.RequestDTO request} 的规则
 *
 * @see com.wakacommerce.cms.web.structure.DisplayContentTag
 * @see com.wakacommerce.cms.structure.service.StructuredContentServiceImpl#evaluateAndPriortizeContent(java.util.List, int, java.util.Map)
 *
 */
public interface StructuredContentRule extends SimpleRule, MultiTenantCloneable<StructuredContentRule> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    public StructuredContentRule cloneEntity();

}
