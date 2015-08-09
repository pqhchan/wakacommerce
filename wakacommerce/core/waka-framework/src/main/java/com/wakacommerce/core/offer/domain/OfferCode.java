
package com.wakacommerce.core.offer.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.persistence.Status;
import com.wakacommerce.core.order.domain.Order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface OfferCode extends Status, Serializable, MultiTenantCloneable<OfferCode> {

    public Long getId() ;

    public void setId(Long id) ;

    public Offer getOffer() ;

    public void setOffer(Offer offer) ;

    public String getOfferCode();

    public void setOfferCode(String offerCode);

    public Date getStartDate();

    public void setStartDate(Date startDate);

    public Date getEndDate();

    public void setEndDate(Date endDate);

    public int getMaxUses();

    public void setMaxUses(int maxUses);

    public boolean isUnlimitedUse();

    public boolean isLimitedUse();

    @Deprecated
    public int getUses() ;

    @Deprecated
    public void setUses(int uses);

    public List<Order> getOrders();

    public void setOrders(List<Order> orders);

}
