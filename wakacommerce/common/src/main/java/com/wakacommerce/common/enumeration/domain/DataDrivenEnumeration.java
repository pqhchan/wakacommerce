
package com.wakacommerce.common.enumeration.domain;

import java.io.Serializable;
import java.util.List;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface DataDrivenEnumeration extends Serializable, MultiTenantCloneable<DataDrivenEnumeration> {
    
    public Long getId();

    public void setId(Long id);

    public String getKey();

    public void setKey(String key);

    public Boolean getModifiable();

    public void setModifiable(Boolean modifiable);

    public List<DataDrivenEnumerationValue> getEnumValues();

    public void setEnumValues(List<DataDrivenEnumerationValue> enumValues);

    @Deprecated
    public List<DataDrivenEnumerationValue> getOrderItems();

    @Deprecated
    public void setOrderItems(List<DataDrivenEnumerationValue> orderItems);

}
