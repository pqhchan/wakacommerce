
package com.wakacommerce.core.offer.dao;

import com.wakacommerce.core.offer.domain.OfferAudit;
import com.wakacommerce.core.offer.service.OfferService;
import com.wakacommerce.core.offer.service.workflow.RecordOfferUsageActivity;
import com.wakacommerce.core.offer.service.workflow.VerifyCustomerMaxOfferUsesActivity;

/**
 * DAO for auditing what went on with offers being added to an order
 *
 *Phillip Verheyden (phillipuniverse)
 * @see {@link VerifyCustomerMaxOfferUsesActivity}, {@link RecordOfferUsageActivity},
 * {@link OfferService#verifyMaxCustomerUsageThreshold(com.wakacommerce.profile.core.domain.Customer, com.wakacommerce.core.offer.domain.OfferCode)}
 */
public interface OfferAuditDao {
    
    public OfferAudit readAuditById(Long offerAuditId);
    
    /**
     * Persists an audit record to the database
     */
    public OfferAudit save(OfferAudit offerAudit);
    
    public void delete(OfferAudit offerAudit);

    /**
     * Creates a new offer audit
     */
    public OfferAudit create();
    
    /**
     * Counts how many times the an offer has been used by a customer
     * 
     * @param customerId
     * @param offerId
     * @return
     */
    public Long countUsesByCustomer(Long customerId, Long offerId);

    /**
     * Counts how many times the given offer code has been used in the system
     * 
     * @param offerCodeId
     * @return
     */
    public Long countOfferCodeUses(Long offerCodeId);

}
