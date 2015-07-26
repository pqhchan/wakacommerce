
package com.wakacommerce.core.offer.domain;

import java.io.Serializable;

import com.wakacommerce.common.money.Money;

public interface Adjustment extends Serializable {

    public Long getId();

    public void setId(Long id);

    public Offer getOffer();

    public String getReason();

    public void setReason(String reason);

    public Money getValue();
    
    public void setValue(Money value);

}
