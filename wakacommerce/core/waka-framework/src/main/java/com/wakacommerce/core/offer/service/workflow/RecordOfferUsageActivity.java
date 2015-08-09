
package com.wakacommerce.core.offer.service.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.common.time.SystemTime;
import com.wakacommerce.core.checkout.service.workflow.CheckoutSeed;
import com.wakacommerce.core.offer.domain.Offer;
import com.wakacommerce.core.offer.domain.OfferAudit;
import com.wakacommerce.core.offer.domain.OfferCode;
import com.wakacommerce.core.offer.service.OfferAuditService;
import com.wakacommerce.core.offer.service.OfferService;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;
import com.wakacommerce.core.workflow.state.ActivityStateManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class RecordOfferUsageActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    public static final String SAVED_AUDITS = "savedAudits";
    
    protected static final Log LOG = LogFactory.getLog(RecordOfferUsageActivity.class);

    @Resource(name="blOfferAuditService")
    protected OfferAuditService offerAuditService;
    
    @Resource(name = "blOfferService")
    protected OfferService offerService;

    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        Order order = context.getSeedData().getOrder();
        Set<Offer> appliedOffers = offerService.getUniqueOffersFromOrder(order);
        Map<Offer, OfferCode> offerToCodeMapping = offerService.getOffersRetrievedFromCodes(order.getAddedOfferCodes(), appliedOffers);
        
        List<OfferAudit> audits = saveOfferIds(appliedOffers, offerToCodeMapping, order);
        
        Map<String, Object> state = new HashMap<String, Object>();
        state.put(SAVED_AUDITS, audits);
        
        ActivityStateManagerImpl.getStateManager().registerState(this, context, getRollbackHandler(), state);

        return context;
    }

    protected List<OfferAudit> saveOfferIds(Set<Offer> offers, Map<Offer, OfferCode> offerToCodeMapping, Order order) {
        List<OfferAudit> audits = new ArrayList<OfferAudit>(offers.size());
        for (Offer offer : offers) {
            OfferAudit audit = offerAuditService.create();
            audit.setCustomerId(order.getCustomer().getId());
            audit.setOfferId(offer.getId());
            audit.setOrderId(order.getId());
            
            //add the code that was used to obtain the offer to the audit context
            OfferCode codeUsedToRetrieveOffer = offerToCodeMapping.get(offer);
            if (codeUsedToRetrieveOffer != null) {
                audit.setOfferCodeId(codeUsedToRetrieveOffer.getId());
            }
            
            audit.setRedeemedDate(SystemTime.asDate());
            audit = offerAuditService.save(audit);
            audits.add(audit);
        }
        
        return audits;
    }
        
}
