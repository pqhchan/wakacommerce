package com.wakacommerce.cms.page.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.openadmin.audit.AdminAuditable;

import java.io.Serializable;

public interface PageField extends Serializable,MultiTenantCloneable<PageField> {

    public Long getId();

    public void setId(Long id);

    public String getFieldKey();

    public void setFieldKey(String fieldKey);

    public String getValue();

    public void setValue(String value);

    public AdminAuditable getAuditable();

    public void setAuditable(AdminAuditable auditable);

    public Page getPage();

    public void setPage(Page page);

}
