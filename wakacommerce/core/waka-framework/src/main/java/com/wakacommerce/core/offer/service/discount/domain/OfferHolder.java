
package com.wakacommerce.core.offer.service.discount.domain;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.core.offer.domain.Offer;


public interface OfferHolder {

    Offer getOffer();

    BroadleafCurrency getCurrency();

}
