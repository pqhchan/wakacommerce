package com.wakacommerce.cms.page.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.ValueAssignable;

public interface PageAttribute extends ValueAssignable<String>,MultiTenantCloneable<PageAttribute> {

    public Long getId();

    public void setId(Long id);

    public Page getPage();

    public void setPage(Page page);

}
