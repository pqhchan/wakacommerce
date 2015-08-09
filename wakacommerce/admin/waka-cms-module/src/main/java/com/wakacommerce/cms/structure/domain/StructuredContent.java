 package com.wakacommerce.cms.structure.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.locale.domain.Locale;
import com.wakacommerce.openadmin.audit.AdminAuditable;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 *
 * @ hui
 */
public interface StructuredContent extends Serializable, MultiTenantCloneable<StructuredContent> {

    @Nullable
    public Long getId();

    public void setId(@Nullable Long id);

    @Nonnull
    public String getContentName();

    public void setContentName(@Nonnull String contentName);

    @Nonnull
    public StructuredContentType getStructuredContentType();

    public void setStructuredContentType(@Nonnull StructuredContentType structuredContentType);

    @Nullable
    public Map<String, StructuredContentFieldXref> getStructuredContentFieldXrefs();

    public void setStructuredContentFieldXrefs(@Nullable Map<String, StructuredContentFieldXref> structuredContentFields);

    @Nullable
    public Boolean getOfflineFlag();

    public void setOfflineFlag(@Nullable Boolean offlineFlag);

    @Nullable
    public Integer getPriority();

    public void setPriority(@Nullable Integer priority);

    @Nullable
    public AdminAuditable getAuditable();

    public void setAuditable(@Nullable AdminAuditable auditable);

    @Nullable
    @Deprecated
    public Map<String, StructuredContentRule> getStructuredContentMatchRules();

    @Deprecated
    public void setStructuredContentMatchRules(@Nullable Map<String, StructuredContentRule> structuredContentMatchRules);

    @Nullable
    @Deprecated
    public Set<StructuredContentItemCriteria> getQualifyingItemCriteria();

    @Deprecated
    public void setQualifyingItemCriteria(@Nullable Set<StructuredContentItemCriteria> qualifyingItemCriteria);

    public String getFieldValue(String fieldName);

    public void setFieldValues(Map<String, String> fieldValuesMap);

    public Map<String, String> getFieldValues();

}
