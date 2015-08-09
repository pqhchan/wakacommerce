
package com.wakacommerce.core.order.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.order.service.type.FulfillmentType;
import com.wakacommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface FulfillmentOption extends Serializable, MultiTenantCloneable<FulfillmentOption> {
    
    public Long getId();
    
    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public String getLongDescription();

    public void setLongDescription(String longDescription);

    public Boolean getUseFlatRates();

    public void setUseFlatRates(Boolean useFlatRates);

    public FulfillmentType getFulfillmentType();

    public void setFulfillmentType(FulfillmentType fulfillmentType);

    public void setTaxCode(String taxCode);

    public String getTaxCode();

    public Boolean getTaxable();

    public void setTaxable(Boolean taxable);

}
