package com.wakacommerce.cms.page.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.locale.domain.Locale;

import java.io.Serializable;
import java.util.List;

public interface PageTemplate extends Serializable, MultiTenantCloneable<PageTemplate> {

    public Long getId();

    public void setId(Long id);

    public String getTemplateName();

    public void setTemplateName(String templateName);

    public String getTemplateDescription();

    public void setTemplateDescription(String templateDescription);

    public String getTemplatePath();

    public void setTemplatePath(String templatePath);

    /**
     * @deprecated in favor of translating individual fields
     * @return
     */
    public Locale getLocale();

    /**
     * @deprecated in favor of translating individual fields
     * @return
     */
    public void setLocale(Locale locale);

    public List<PageTemplateFieldGroupXref> getFieldGroupXrefs();

    public void setFieldGroupXrefs(List<PageTemplateFieldGroupXref> fieldGroups);

}
