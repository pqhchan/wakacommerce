package com.wakacommerce.cms.page.domain;

import java.io.Serializable;
import java.util.List;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface PageTemplate extends Serializable, MultiTenantCloneable<PageTemplate> {

    public Long getId();

    public void setId(Long id);

    public String getTemplateName();

    public void setTemplateName(String templateName);

    public String getTemplateDescription();

    public void setTemplateDescription(String templateDescription);

    public String getTemplatePath();

    public void setTemplatePath(String templatePath);

    public List<PageTemplateFieldGroupXref> getFieldGroupXrefs();

    public void setFieldGroupXrefs(List<PageTemplateFieldGroupXref> fieldGroups);

}
