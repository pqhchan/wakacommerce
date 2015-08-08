package com.wakacommerce.cms.page.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.openadmin.audit.AdminAuditImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

public interface Page extends Serializable, MultiTenantCloneable<Page> {

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

    public AdminAuditImpl getAuditable();

    public void setAuditable(AdminAuditImpl auditable);
    
    @Nullable
    public Boolean getOfflineFlag();

    public void setOfflineFlag(@Nullable Boolean offlineFlag);
    
    /**
     * Returns a map of the targeting rules associated with this page.
     *
     * Targeting rules are defined in the content mangagement system and used to
     * enforce which page is returned to the client.
     *
     * @return
     */
    @Nullable
    public Map<String, PageRule> getPageMatchRules();

    /**
     * Sets the targeting rules for this content item.
     *
     * @param pageRules
     */
    public void setPageMatchRules(@Nullable Map<String, PageRule> pageRules);
    
    /**
     * Returns the item (or cart) based rules associated with this content item.
     *
     * @return
     */
    @Nullable
    public Set<PageItemCriteria> getQualifyingItemCriteria();

    /**
     * Sets the item (e.g. cart) based rules associated with this content item.
     *
     * @param qualifyingItemCriteria
     */
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
