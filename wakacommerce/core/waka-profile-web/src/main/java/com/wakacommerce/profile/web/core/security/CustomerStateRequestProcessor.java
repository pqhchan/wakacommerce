  
package com.wakacommerce.profile.web.core.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.util.BLCRequestUtils;
import com.wakacommerce.common.web.AbstractWakaWebRequestProcessor;
import com.wakacommerce.common.web.BroadleafRequestCustomerResolverImpl;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.CustomerService;
import com.wakacommerce.profile.web.core.CustomerState;
import com.wakacommerce.profile.web.core.CustomerStateRefresher;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


/**
 *
 * @ hui
 */
@Component("blCustomerStateRequestProcessor")
public class CustomerStateRequestProcessor extends AbstractWakaWebRequestProcessor implements ApplicationEventPublisherAware {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public static final String BLC_RULE_MAP_PARAM = "blRuleMap";

    @Resource(name="blCustomerService")
    protected CustomerService customerService;
    
    @Resource(name = "blCustomerMergeExtensionManager")
    protected CustomerMergeExtensionManager customerMergeExtensionManager;

    protected ApplicationEventPublisher eventPublisher;

    public static final String ANONYMOUS_CUSTOMER_SESSION_ATTRIBUTE_NAME = "_blc_anonymousCustomer";
    public static final String ANONYMOUS_CUSTOMER_ID_SESSION_ATTRIBUTE_NAME = "_blc_anonymousCustomerId";
    private static final String LAST_PUBLISHED_EVENT_CLASS_SESSION_ATTRIBUTE_NAME = "_blc_lastPublishedEventClass";
    private static final String LAST_PUBLISHED_EVENT_USERNAME_SESSION_ATTRIBUTE_NAME = "_blc_lastPublishedEventUsername";
    public static final String OVERRIDE_CUSTOMER_SESSION_ATTR_NAME = "_blc_overrideCustomerId";
    public static final String ANONYMOUS_CUSTOMER_MERGED_SESSION_ATTRIBUTE_NAME = "_blc_anonymousCustomerMerged";

    @Override
    public void process(WebRequest request) {
        Customer customer = null;
        Long overrideId = null;
        if (BLCRequestUtils.isOKtoUseSession(request)) {
            overrideId = (Long) request.getAttribute(OVERRIDE_CUSTOMER_SESSION_ATTR_NAME, WebRequest.SCOPE_GLOBAL_SESSION);
        }
        if (overrideId != null) {
            customer = customerService.readCustomerById(overrideId);
            if (customer != null && !customer.isRegistered() && !customer.isLoggedIn() && !customer.isCookied()) {
                customer.setAnonymous(true);
            }
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if ((authentication != null) && !(authentication instanceof AnonymousAuthenticationToken)) {
                String userName = authentication.getName();
                customer = (Customer) BroadleafRequestCustomerResolverImpl.getRequestCustomerResolver().getCustomer(request);
                if (userName != null && (customer == null || !userName.equals(customer.getUsername()))) {
                    // can only get here if the authenticated user does not match the user in session
                    customer = customerService.readCustomerByUsername(userName);
                    if (logger.isDebugEnabled() && customer != null) {
                        logger.debug("Customer found by username " + userName);
                    }
                }
                if (customer != null) {
                    String lastPublishedEventClass = (String) BLCRequestUtils.getSessionAttributeIfOk(request, LAST_PUBLISHED_EVENT_CLASS_SESSION_ATTRIBUTE_NAME);
                    String eventUsername = (String) BLCRequestUtils.getSessionAttributeIfOk(request, LAST_PUBLISHED_EVENT_USERNAME_SESSION_ATTRIBUTE_NAME);
                    
                    if (authentication instanceof RememberMeAuthenticationToken) {
                        // set transient property of customer
                        customer.setCookied(true);
                        boolean publishRememberMeEvent = true;
                        if (CustomerAuthenticatedFromCookieEvent.class.getName().equals(lastPublishedEventClass)) {
                            if (userName.equals(eventUsername)) {
                                publishRememberMeEvent = false;
                            }
                        }
                        if (publishRememberMeEvent) {
                            CustomerAuthenticatedFromCookieEvent cookieEvent = new CustomerAuthenticatedFromCookieEvent(customer, this.getClass().getName());
                            publishEvent(cookieEvent, request, CustomerAuthenticatedFromCookieEvent.class.getName(), userName);
                        }
                    } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                        customer.setLoggedIn(true);
                        boolean publishLoggedInEvent = true;
                        if (CustomerLoggedInEvent.class.getName().equals(lastPublishedEventClass)) {
                            if (userName.equals(eventUsername)) {
                                publishLoggedInEvent = false;
                            }
                        }
                        if (publishLoggedInEvent) {
                            CustomerLoggedInEvent loggedInEvent = new CustomerLoggedInEvent(customer, this.getClass().getName()); 
                            publishEvent(loggedInEvent, request, CustomerLoggedInEvent.class.getName(), userName);
                        }
                    } else {
                        customer = resolveAuthenticatedCustomer(authentication);
                    }
                }
            }
        }

        if (customer == null) {
            // This is an anonymous customer.
            // TODO: Handle a custom cookie (different than remember me) that is just for anonymous users.  
            // This can be used to remember their cart from a previous visit.
            // Cookie logic probably needs to be configurable - with TCS as the exception.

            customer = resolveAnonymousCustomer(request);
        } else {
            //Does this customer need to have an anonymous customer's data merged into it?
            customer = mergeCustomerIfRequired(request, customer);
        }
        CustomerState.setCustomer(customer);

        // Setup customer for content rule processing
        @SuppressWarnings("unchecked")
        Map<String,Object> ruleMap = (Map<String, Object>) request.getAttribute(BLC_RULE_MAP_PARAM, WebRequest.SCOPE_REQUEST);
        if (ruleMap == null) {
            ruleMap = new HashMap<String,Object>();
        }
        ruleMap.put("customer", customer);
        request.setAttribute(BLC_RULE_MAP_PARAM, ruleMap, WebRequest.SCOPE_REQUEST);
        
    }
    
    protected void publishEvent(ApplicationEvent event, WebRequest request, String eventClass, String username) {
        eventPublisher.publishEvent(event);
        BLCRequestUtils.setSessionAttributeIfOk(request, LAST_PUBLISHED_EVENT_CLASS_SESSION_ATTRIBUTE_NAME, eventClass);
        BLCRequestUtils.setSessionAttributeIfOk(request, LAST_PUBLISHED_EVENT_USERNAME_SESSION_ATTRIBUTE_NAME, username);
    }

    protected Customer mergeCustomerIfRequired(WebRequest request, Customer customer) {
        if (BLCRequestUtils.isOKtoUseSession(request)) {
            //Don't call this if it has already been called
            if (request.getAttribute(getAnonymousCustomerMergedSessionAttributeName(), WebRequest.SCOPE_GLOBAL_SESSION) == null) {
                //Set this so we don't do this every time.
                request.setAttribute(getAnonymousCustomerMergedSessionAttributeName(), Boolean.TRUE, WebRequest.SCOPE_GLOBAL_SESSION);

                Customer anonymousCustomer = getAnonymousCustomer(request);
                customer = copyAnonymousCustomerInfoToCustomer(request, anonymousCustomer, customer);
            }
        }
        return customer;
    }

    protected Customer copyAnonymousCustomerInfoToCustomer(WebRequest request, Customer anonymous, Customer customer) {
        if (customerMergeExtensionManager != null) {
            ExtensionResultHolder<Customer> resultHolder = new ExtensionResultHolder<Customer>();
            resultHolder.setResult(customer);
            customerMergeExtensionManager.getProxy().merge(resultHolder, request, anonymous);
            
            if (resultHolder.getThrowable() != null) {
                if (resultHolder.getThrowable() instanceof RuntimeException) {
                    throw ((RuntimeException) resultHolder.getThrowable());
                } else {
                    throw new RuntimeException("An unexpected error occured merging the anonymous customer",
                            resultHolder.getThrowable());
                }
            }
            
            return customerService.saveCustomer(resultHolder.getResult());
        }
        return customer;
    }

    public Customer resolveAuthenticatedCustomer(Authentication authentication) {
        return null;
    }

    public Customer resolveAnonymousCustomer(WebRequest request) {
        Customer customer;
        customer = getAnonymousCustomer(request);
        
        //If there is no Customer object in session, AND no customer id in session, create a new customer
        //and store the entire customer in session (don't persist to DB just yet)
        if (customer == null) {
            customer = customerService.createNewCustomer();
            if (BLCRequestUtils.isOKtoUseSession(request)) {
                request.setAttribute(getAnonymousCustomerSessionAttributeName(), customer, WebRequest.SCOPE_GLOBAL_SESSION);
            }
        }
        customer.setAnonymous(true);

        return customer;
    }

    public Customer getAnonymousCustomer(WebRequest request) {
        if (BLCRequestUtils.isOKtoUseSession(request)) {
            Customer anonymousCustomer = (Customer) request.getAttribute(getAnonymousCustomerSessionAttributeName(),
                    WebRequest.SCOPE_GLOBAL_SESSION);
            if (anonymousCustomer == null) {
                //Customer is not in session, see if we have just a customer ID in session (the anonymous customer might have
                //already been persisted)
                Long customerId = (Long) request.getAttribute(getAnonymousCustomerIdSessionAttributeName(), WebRequest.SCOPE_GLOBAL_SESSION);
                if (customerId != null) {
                    //we have a customer ID in session, look up the customer from the database to ensure we have an up-to-date
                    //customer to store in CustomerState
                    anonymousCustomer = customerService.readCustomerById(customerId);
                }
            }
            return anonymousCustomer;
        }
        return null;
    }

    public static String getAnonymousCustomerSessionAttributeName() {
        return ANONYMOUS_CUSTOMER_SESSION_ATTRIBUTE_NAME;
    }

    public static String getAnonymousCustomerIdSessionAttributeName() {
        return ANONYMOUS_CUSTOMER_ID_SESSION_ATTRIBUTE_NAME;
    }
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public static String getCustomerRequestAttributeName() {
        return BroadleafRequestCustomerResolverImpl.getRequestCustomerResolver().getCustomerRequestAttributeName();
    }

    public static String getAnonymousCustomerMergedSessionAttributeName() {
        return ANONYMOUS_CUSTOMER_MERGED_SESSION_ATTRIBUTE_NAME;
    }
}
