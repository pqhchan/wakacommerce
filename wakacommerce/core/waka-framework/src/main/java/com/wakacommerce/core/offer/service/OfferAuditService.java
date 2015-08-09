
package com.wakacommerce.core.offer.service;

import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferAudit;
import com.wakacommerce.profile.core.domain.Customer;


/**
 *
 * @ hui
 */
public interface OfferAuditService {

    public OfferAudit readAuditById(Long offerAuditId);

    public OfferAudit save(OfferAudit offerAudit);
    
    public void delete(OfferAudit offerAudit);

    public OfferAudit create();

    public Long countUsesByCustomer(Long customerId, Long offerId);

    public Long countOfferCodeUses(Long offerCodeId);
    
}
