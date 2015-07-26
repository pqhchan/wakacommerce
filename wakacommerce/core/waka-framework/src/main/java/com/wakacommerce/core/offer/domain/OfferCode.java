
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

    /**
     * Returns the maximum number of times that this code can be used regardless of Order or Customer
     *
     * 0 indicates unlimited usage.
     *
     * @return
     */
    public int getMaxUses();

    /**
     * Sets the maximum number of times that this code can be used regardless of Order or Customer
     *
     * 0 indicates unlimited usage.
     *
     * @param maxUses
     */
    public void setMaxUses(int maxUses);

    /**
     * Indicates that this is an unlimited-use code. By default this is true if {@link #getMaxUses()} == 0
     */
    public boolean isUnlimitedUse();
    
    /**
     * Indicates that this code has a limit on how many times it can be used. By default this is true if {@link #getMaxUses()} > 0
     */
    public boolean isLimitedUse();
    
    /**
     * @deprecated replaced by the {@link OfferAudit} table
     */
    @Deprecated
    public int getUses() ;

    /**
     * @deprecated replaced by the {@link OfferAudit} table
     */
    @Deprecated
    public void setUses(int uses);

    public List<Order> getOrders();

    public void setOrders(List<Order> orders);

}
