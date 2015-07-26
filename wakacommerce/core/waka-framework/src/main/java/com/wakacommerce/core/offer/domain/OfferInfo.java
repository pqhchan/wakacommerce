
package com.wakacommerce.core.offer.domain;

import java.io.Serializable;
import java.util.Map;

import com.wakacommerce.common.copy.MultiTenantCloneable;

public interface OfferInfo extends Serializable, MultiTenantCloneable<OfferInfo>{

    public Long getId();

    public void setId(Long id);

    public Map<String, String> getFieldValues();

    public void setFieldValues(Map<String, String> fieldValues);

}
