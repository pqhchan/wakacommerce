
package com.wakacommerce.core.inventory.domain;

import java.io.Serializable;
import java.util.Date;

import com.wakacommerce.core.inventory.service.type.AvailabilityStatusType;
/**
 *
 * @ hui
 */
@Deprecated
public interface SkuAvailability extends Serializable {

    public Long getId();

    public void setId(Long id);

    public Long getSkuId();

    public void setSkuId(Long id);

    public Long getLocationId();

    public void setLocationId(Long id);

    public AvailabilityStatusType getAvailabilityStatus();

    public void setAvailabilityStatus(AvailabilityStatusType status);

    public Date getAvailabilityDate();

    public void setAvailabilityDate(Date availabilityDate);

    public Integer getQuantityOnHand();

    public void setQuantityOnHand(Integer quantityOnHand);

    public Integer getReserveQuantity();

    public void setReserveQuantity(Integer reserveQuantity);

    public Integer getAvailableQuantity();
}
