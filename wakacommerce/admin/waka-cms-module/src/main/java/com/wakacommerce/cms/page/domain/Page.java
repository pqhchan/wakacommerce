package com.wakacommerce.cms.page.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.openadmin.audit.AdminAuditable;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public interface Page extends Serializable,MultiTenantCloneable<Page> {

    public Long getId();

    public void setId(Long id);

    public String getFullUrl();

    public void setFullUrl(String fullUrl);

    public String getDescription();

    public void setDescription(String description);

    public PageTemplate getPageTemplate();

    public void setPageTemplate(PageTemplate pageTemplate);

    public Map<String, PageField> getPageFields();

    public void setPageFields(Map<String, PageField> pageFields);

    public AdminAuditable getAuditable();

    public void setAuditable(AdminAuditable auditable);

    @Nullable
    public Boolean getOfflineFlag();

    public void setOfflineFlag(@Nullable Boolean offlineFlag);

    @Nullable
    public Integer getPriority();

    public void setPriority(@Nullable Integer priority);

    @Nullable
    public Map<String, PageRule> getPageMatchRules();

    public void setPageMatchRules(@Nullable Map<String, PageRule> pageRules);

    @Nullable
    public Set<PageItemCriteria> getQualifyingItemCriteria();

    public void setQualifyingItemCriteria(@Nullable Set<PageItemCriteria> qualifyingItemCriteria);

    @Nullable
    public boolean getExcludeFromSiteMap();

    public void setExcludeFromSiteMap(boolean excludeFromSiteMap);

    public Map<String, PageAttribute> getAdditionalAttributes();

    public void setAdditionalAttributes(Map<String, PageAttribute> additionalAttributes);
    
    public Date getActiveStartDate();
    
    public void setActiveStartDate(Date activeStartDate);
    
    public Date getActiveEndDate();
    
    public void setActiveEndDate(Date activeEndDate);
    
    public String getMetaTitle();
    
    public void setMetaTitle(String metaTitle);
    
    public String getMetaDescription();
    
    public void setMetaDescription(String metaDescription);

}
