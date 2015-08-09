
package com.wakacommerce.core.offer.dao;

import com.wakacommerce.core.offer.domain.OfferAudit;
import com.wakacommerce.core.offer.service.OfferService;
import com.wakacommerce.core.offer.service.workflow.RecordOfferUsageActivity;
import com.wakacommerce.core.offer.service.workflow.VerifyCustomerMaxOfferUsesActivity;

/**
 *
 * @ hui
 */
public interface OfferAuditDao {
    
    public OfferAudit readAuditById(Long offerAuditId);

    public OfferAudit save(OfferAudit offerAudit);
    
    public void delete(OfferAudit offerAudit);

    public OfferAudit create();

    public Long countUsesByCustomer(Long customerId, Long offerId);

    public Long countOfferCodeUses(Long offerCodeId);

}
