
package com.wakacommerce.common.enumeration.domain;

import java.io.Serializable;

import com.wakacommerce.common.copy.MultiTenantCloneable;

/**
 * 
 */
public interface DataDrivenEnumerationValue extends Serializable, MultiTenantCloneable<DataDrivenEnumerationValue> {
    
    public String getDisplay();

    public void setDisplay(String display);

    public Boolean getHidden();

    public void setHidden(Boolean hidden);

    public Long getId();

    public void setId(Long id);

    public String getKey();

    public void setKey(String key);

    public DataDrivenEnumeration getType();

    public void setType(DataDrivenEnumeration type);

}
