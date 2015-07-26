
package com.wakacommerce.core.offer.dao;

import com.wakacommerce.core.offer.domain.OfferCode;

public interface OfferCodeDao {

    public OfferCode readOfferCodeById(Long offerCode);

    public OfferCode readOfferCodeByCode(String code);

    public OfferCode save(OfferCode offerCode);

    public void delete(OfferCode offerCodeId);

    public OfferCode create();

}
