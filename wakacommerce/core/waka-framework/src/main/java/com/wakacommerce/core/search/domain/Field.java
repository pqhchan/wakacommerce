
package com.wakacommerce.core.search.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.core.search.domain.solr.FieldType;

import java.util.List;

/**
 *
 * @ hui
 */
public interface Field extends MultiTenantCloneable<Field> {

    public Long getId();

    public void setId(Long id);

    public void setFriendlyName(String friendlyName);

    public String getFriendlyName();

    public FieldEntity getEntityType();

    public void setEntityType(FieldEntity entityType);

    public String getPropertyName();

    public void setPropertyName(String propertyName);

    public String getAbbreviation();

    public void setAbbreviation(String abbreviation);

    public Boolean getSearchable();

    public void setSearchable(Boolean searchable);

    public void setFacetFieldType(FieldType facetFieldType);

    public FieldType getFacetFieldType();

    public void setSearchableFieldTypes(List<FieldType> searchableFieldTypes);

    public List<FieldType> getSearchableFieldTypes();

    public List<SearchConfig> getSearchConfigs();

    public void setSearchConfigs (List<SearchConfig> searchConfigs);

    public String getQualifiedFieldName();

    public Boolean getTranslatable();

    public void setTranslatable(Boolean translatable);
}
