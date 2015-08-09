
package com.wakacommerce.core.offer.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @ hui
 */
public interface OfferAudit extends Serializable {

    public Long getId();

    public void setId(Long id);

    public Long getOfferId();

    public void setOfferId(Long offerId);

    public Long getOfferCodeId();

    public void setOfferCodeId(Long offerCodeId);

    public Long getOrderId();

    public void setOrderId(Long orderId);

    public Long getCustomerId();

    public void setCustomerId(Long customerId);

    public Date getRedeemedDate();

    public void setRedeemedDate(Date redeemedDate);
}
