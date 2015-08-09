  
package com.wakacommerce.profile.web.core;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.util.BLCRequestUtils;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.domain.CustomerPersistedEvent;
import com.wakacommerce.profile.web.core.security.CustomerStateRequestProcessor;


/**
 *
 * @ hui
 */
@Component("blCustomerStateRefresher")
public class CustomerStateRefresher implements ApplicationListener<CustomerPersistedEvent> {

    @Override
    public void onApplicationEvent(final CustomerPersistedEvent event) {
        Customer dbCustomer = event.getCustomer();

        //if there is an active request, remove the session-based customer if it exists and update CustomerState
        WebRequest request = WakaRequestContext.getWakaRequestContext().getWebRequest();
        if (request != null) {
            String customerAttribute = CustomerStateRequestProcessor.getAnonymousCustomerSessionAttributeName();
            String customerIdAttribute = CustomerStateRequestProcessor.getAnonymousCustomerIdSessionAttributeName();
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                Customer sessionCustomer = (Customer) request.getAttribute(customerAttribute, WebRequest.SCOPE_GLOBAL_SESSION);
                //invalidate the session-based customer if it's there and the ID is the same as the Customer that has been
                //persisted
                if (sessionCustomer != null && sessionCustomer.getId().equals(dbCustomer.getId())) {
                    request.removeAttribute(customerAttribute, WebRequest.SCOPE_GLOBAL_SESSION);
                    request.setAttribute(customerIdAttribute, dbCustomer.getId(), WebRequest.SCOPE_GLOBAL_SESSION);
                }
            }
            
            //Update CustomerState if the persisted Customer ID is the same
            if (CustomerState.getCustomer() != null && CustomerState.getCustomer().getId().equals(dbCustomer.getId())) {
                //Copy transient fields from the customer that existed in CustomerState, prior to the DB refresh, 
                //to the customer that has been saved (merged) in the DB....
                Customer preMergedCustomer = CustomerState.getCustomer();
                resetTransientFields(preMergedCustomer, dbCustomer);

                CustomerState.setCustomer(dbCustomer);
            }
        }
    }

    protected void resetTransientFields(Customer preMergedCustomer, Customer postMergedCustomer) {
        postMergedCustomer.setUnencodedPassword(preMergedCustomer.getUnencodedPassword());
        postMergedCustomer.setUnencodedChallengeAnswer(preMergedCustomer.getUnencodedChallengeAnswer());
        postMergedCustomer.setAnonymous(preMergedCustomer.isAnonymous());
        postMergedCustomer.setCookied(preMergedCustomer.isCookied());
        postMergedCustomer.setLoggedIn(preMergedCustomer.isLoggedIn());
        postMergedCustomer.getTransientProperties().putAll(preMergedCustomer.getTransientProperties());
    }

}
