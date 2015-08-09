
package com.wakacommerce.core.offer.domain;


import java.math.BigDecimal;


/**
 *
 * @ hui
 */
public interface OfferTier extends Comparable<OfferTier>{

    Long getId();

    void setId(Long id);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);

    Long getMinQuantity();

    void setMinQuantity(Long minQuantity);

    Offer getOffer();

    void setOffer(Offer offer);

}
