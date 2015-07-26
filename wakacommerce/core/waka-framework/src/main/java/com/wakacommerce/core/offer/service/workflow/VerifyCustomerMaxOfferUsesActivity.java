
package com.wakacommerce.core.offer.service.workflow;

import java.util.Set;

import javax.annotation.Resource;

import com.wakacommerce.core.checkout.service.workflow.CheckoutSeed;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.OfferAuditService;
import com.wakacommerce.core.offer.service.OfferService;
import com.wakacommerce.core.offer.service.exception.OfferMaxUseExceededException;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

/**
 * <p>Checks the offers being used in the order to make sure that the customer
 * has not exceeded the max uses for the {@link Offer}.</p>
 * 
 * This will also verify that max uses for any {@link OfferCode}s that were used to retrieve the {@link Offer}s.
 * 
 *Phillip Verheyden (phillipuniverse)
 */
public class VerifyCustomerMaxOfferUsesActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Resource(name="blOfferAuditService")
    protected OfferAuditService offerAuditService;
    
    @Resource(name = "blOfferService")
    protected OfferService offerService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();
        Set<Offer> appliedOffers = offerService.getUniqueOffersFromOrder(order);
        
        for (Offer offer : appliedOffers) {
            if (offer.isLimitedUsePerCustomer()) {
                Long currentUses = offerAuditService.countUsesByCustomer(order.getCustomer().getId(), offer.getId());
                if (currentUses >= offer.getMaxUsesPerCustomer()) {
                    throw new OfferMaxUseExceededException("The customer has used this offer more than the maximum allowed number of times.");
                }
            }
        }
        
        //TODO: allow lenient checking on offer code usage
        for (OfferCode code : order.getAddedOfferCodes()) {
            if (code.isLimitedUse()) {
                Long currentCodeUses = offerAuditService.countOfferCodeUses(code.getId());
                if (currentCodeUses >= code.getMaxUses()) {
                    throw new OfferMaxUseExceededException("Offer code " + code.getOfferCode() + " with id " + code.getId()
                            + " has been than the maximum allowed number of times.");
                }
            }
        }
        
        return context;
    }
   
}
