package com.wakacommerce.common.config.domain;

import java.io.Serializable;

import com.wakacommerce.common.config.service.type.SystemPropertyFieldType;
import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 *
 * @ hui
 */
public interface SystemProperty extends Serializable, MultiTenantCloneable<SystemProperty> {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public String getValue();

    public void setValue(String value);

    public SystemPropertyFieldType getPropertyType();

    public void setPropertyType(SystemPropertyFieldType type);

    public String getFriendlyName();

    public void setFriendlyName(String friendlyName);

    public String getFriendlyGroup();

    public void setFriendlyGroup(String friendlyGroup);

    public String getFriendlyTab();

    public void setFriendlyTab(String friendlyTab);

}
