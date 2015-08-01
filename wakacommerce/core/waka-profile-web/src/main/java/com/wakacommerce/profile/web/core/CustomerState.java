package com.wakacommerce.profile.web.core;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.BroadleafRequestCustomerResolverImpl;
import com.wakacommerce.profile.core.domain.Customer;

import javax.servlet.http.HttpServletRequest;

/**
 * Convenient class to get the active customer from the current request. This state is kept up-to-date in regards to the database
 * throughout the lifetime of the request via the {@link CustomerStateRefresher}.
 */
@Component("blCustomerState")
public class CustomerState {
    
    public static Customer getCustomer(HttpServletRequest request) {
        return (Customer) BroadleafRequestCustomerResolverImpl.getRequestCustomerResolver().getCustomer(request);
    }
    
    public static Customer getCustomer(WebRequest request) {
        return (Customer) BroadleafRequestCustomerResolverImpl.getRequestCustomerResolver().getCustomer(request);
    }
    
    public static Customer getCustomer() {
        if (WakaRequestContext.getWakaRequestContext() == null
                || WakaRequestContext.getWakaRequestContext().getWebRequest() == null) {
            return null;
        }
        return (Customer) BroadleafRequestCustomerResolverImpl.getRequestCustomerResolver().getCustomer();
    }
    
    public static void setCustomer(Customer customer) {
        BroadleafRequestCustomerResolverImpl.getRequestCustomerResolver().setCustomer(customer);
    }

}
